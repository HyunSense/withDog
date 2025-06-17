package withdog.domain.place.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import withdog.common.dto.SliceInfo;
import withdog.common.dto.response.SliceResponseDto;
import withdog.domain.place.dto.response.PlaceResponseDto;
import withdog.domain.place.entity.Place;
import withdog.domain.place.repository.filter.DynamicQueryFilterBuilder;

import java.util.List;

@RequiredArgsConstructor
public class PlaceRepositoryDynamicImpl implements PlaceRepositoryDynamic {

    private final EntityManager em;

    @Override
    public SliceResponseDto<PlaceResponseDto> searchPlacesWithMultiFilters(String keyword, List<String> city, List<String> types,
                                                    List<String> petAccessTypes, List<String> petSizes,
                                                    List<String> services, Pageable pageable) {

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

        // 2. 쿼리 실행
        TypedQuery<Place> query = em.createQuery(queryBuilder.getJpql(), Place.class);
        queryBuilder.getParams().forEach((key, value) -> query.setParameter(key, value));

        int offset = (int) pageable.getOffset();
        int size = pageable.getPageSize();

        query.setFirstResult(offset);
        query.setMaxResults(size + 1);
        List<Place> places = query.getResultList();
        boolean hasNext = places.size() > size;
        if (hasNext) {
            places = places.subList(0, size);
        }

        SliceInfo sliceInfo = new SliceInfo(pageable.getPageNumber(), pageable.getPageSize(), pageable.getPageNumber() == 0, !hasNext, hasNext);
        return new SliceResponseDto<>(sliceInfo, PlaceResponseDto.fromEntityList(places));
    }

    @Override
    public Long getSearchPlacesTotalCount(String keyword, List<String> city, List<String> types,
                                          List<String> petAccessTypes, List<String> petSizes,
                                          List<String> services) {

        // 1. 쿼리 빌더 생성
        DynamicQueryFilterBuilder queryBuilder = new DynamicQueryFilterBuilder();
        queryBuilder
                .baseCountQuery()
                .withKeyword(keyword)
                .withOrFilter("city", city)
                .withOrFilter("types", types)
                .withOrFilter("petAccessTypes", petAccessTypes)
                .withAndFilter("petSizes", petSizes)
                .withAndFilter("services", services);

        // 2. 쿼리 실행
        TypedQuery<Long> query = em.createQuery(queryBuilder.getJpql(), Long.class);
        queryBuilder.getParams().forEach((key, value) -> query.setParameter(key, value));

        return query.getSingleResult();
    }
}
