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
    public Slice<Place> searchPlacesWithMultiFilters(String keyword, List<String> city, List<String> types,
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
        List<Place> resultList = query.getResultList();
        boolean hasNext = resultList.size() > size;
        if (hasNext) {
            resultList = resultList.subList(0, size);
        }

        return new SliceImpl<>(resultList, pageable, hasNext);
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
