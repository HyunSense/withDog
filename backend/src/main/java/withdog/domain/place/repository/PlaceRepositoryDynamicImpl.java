package withdog.domain.place.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import withdog.domain.place.entity.Place;

import java.util.List;

@RequiredArgsConstructor
public class PlaceRepositoryDynamicImpl implements PlaceRepositoryDynamic {

    private final EntityManager em;

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

//    public Slice<Place> findAllPlacesByTypeAndKeyword(String type, String keyword, Pageable pageable) {
//
//        String jpql = "select p from Place p join fetch p.category where 1=1";
//
//        if (type.equals("name")) {
//            jpql += " and p.name like :keyword";
//        }
//
//        if (type.equals("area")) {
//            jpql += " and p.addressPart1 like :keyword";
//        }
//
//        TypedQuery<Place> query = em.createQuery(jpql, Place.class);
//        query.setParameter("keyword", "%" + keyword + "%");
//        query.setFirstResult((int) pageable.getOffset());
//        query.setMaxResults(pageable.getPageSize() + 1); // 다음 페이지 여부 확인
//
//        List<Place> resultList = query.getResultList();
//
//        boolean hasNext = resultList.size() > pageable.getPageSize();
//        if (hasNext) {
//            resultList.remove(resultList.size() - 1);
//        }
//
//        return new SliceImpl<>(resultList, pageable, hasNext);
//    }

}
