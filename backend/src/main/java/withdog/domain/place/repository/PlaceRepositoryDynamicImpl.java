package withdog.domain.place.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import withdog.domain.place.entity.Place;
import withdog.domain.place.repository.filter.DynamicQueryFilterBuilder;

import java.util.List;

@RequiredArgsConstructor
public class PlaceRepositoryDynamicImpl implements PlaceRepositoryDynamic {

    private final EntityManager em;

    @Override
    public Page<Place> findAllPlacesByTypeAndKeyword(String type, String keyword, Pageable pageable) {

        String jpql = "select p from Place p join fetch p.category where 1=1";
        String countJpql = "select count(p) from Place p where 1=1";

        if (type.equals("name")) {
            jpql += " and p.name like :keyword";
            countJpql += " and p.name like :keyword";
        }

        if (type.equals("area")) {
            jpql += " and p.addressPart1 like :keyword";
            countJpql += " and p.addressPart1 like :keyword";
        }

        // 데이터 쿼리
        TypedQuery<Place> query = em.createQuery(jpql, Place.class);
        query.setParameter("keyword", "%" + keyword + "%");
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Place> resultList = query.getResultList();

        // Count 쿼리
        TypedQuery<Long> countQuery = em.createQuery(countJpql, Long.class);
        countQuery.setParameter("keyword", "%" + keyword + "%");
        Long totalCount = countQuery.getSingleResult();

        return new PageImpl<>(resultList, pageable, totalCount);
    }

    @Override
    public List<Place> searchPlacesWithMultiFiltersUsingSubQuery() {

        String jpql = "select p from Place p where " +
                "p.name like concat('%', :keyword, '%') " +
                "and exists ( " + // [OR 조건] 지역 (인천 OR 서울)
                "select 1 from p.placeFilters pf " +
                "join pf.filterOption fo " +
                "join fo.filterCategory fc " +
                "where pf.place = p " +
                "and fc.name = 'city' " +
                "and fo.value in ('인천', '서울') " +
                ") " +
                "and exists ( " + // [OR 조건] 유형 (펜션 OR 캠핑)
                "select 1 from p.placeFilters pf " +
                "join pf.filterOption fo " +
                "join fo.filterCategory fc " +
                "where pf.place = p " +
                "and fc.name = 'types' " +
                "and fo.value in ('pension', 'camp') " +
                ") " +
                "and exists ( " + // [OR 조건] 반려견 이용 유형 (전용 OR 동반)
                "select 1 from p.placeFilters pf " +
                "join pf.filterOption fo " +
                "join fo.filterCategory fc " +
                "where pf.place = p " +
                "and fc.name = 'petAccessTypes' " +
                "and fo.value in ('petOnly', 'petFriendly') " +
                ") " +
                "and ( " + // [AND 조건] 허용견 크기 (소형견 AND 중형견)
                "select count(distinct fo.value) from p.placeFilters pf " +
                "join pf.filterOption fo " +
                "join fo.filterCategory fc " +
                "where pf.place = p " +
                "and fc.name = 'petSizes' " +
                "and fo.value in ('sm', 'md') " +
                ") = 2 " +
                "and ( " + // [AND 조건] 편의시설 (주차장 AND 야외시설)
                "select count(distinct fo.value) from p.placeFilters pf " +
                "join pf.filterOption fo " +
                "join fo.filterCategory fc " +
                "where pf.place = p " +
                "and fc.name = 'services' " +
                "and fo.value in ('parking', 'outdoorFacilities') " +
                ") = 2 ";
        TypedQuery<Place> query = em.createQuery(jpql, Place.class);
        query.setParameter("keyword", "noImg");
        List<Place> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public Slice<Place> searchPlacesWithMultiFilters(String keyword, List<String> city, List<String> types,
                                                    List<String> petAccessTypes, List<String> petSizes,
                                                    List<String> services, Pageable pageable) {
//                                                    List<String> services, int offset, int size, String sort) {
        // 1. 쿼리 빌더 생성
        DynamicQueryFilterBuilder queryBuilder = new DynamicQueryFilterBuilder();
        queryBuilder
                .baseQuery()
                .withKeyword(keyword)
                .withOrFilter("city", city)
                .withOrFilter("types", types)
                .withOrFilter("petAccessTypes", petAccessTypes)
                .withAndFilter("petSizes", petSizes)
                .withAndFilter("services", services);
//                .withPageNation((int) pageable.getOffset(), pageable.getPageSize());

        // 2. 쿼리 실행
        TypedQuery<Place> query = em.createQuery(queryBuilder.getJpql(), Place.class);
        queryBuilder.getParams().forEach((key, value) -> query.setParameter(key, value));

        //수정코드 start
        int offset = (int) pageable.getOffset();
        int size = pageable.getPageSize();
        //수정코드 end
        query.setFirstResult(offset);
        query.setMaxResults(size + 1);
        List<Place> resultList = query.getResultList();
        boolean hasNext = resultList.size() > size;
        if (hasNext) {
            resultList = resultList.subList(0, size);
        }

        return new SliceImpl<>(resultList, pageable, hasNext);
//        return resultList;
    }
}
