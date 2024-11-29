package withdog.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import withdog.entity.Category;
import withdog.entity.Place;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DynamicQueryPlaceRepository {

    private final EntityManager em;

    public List<Place> findTop3ByCategoryOrderByHitAndBookmarkCountDesc(Category category) {

        String jpql = "select p from Place p";
        String orderBy = " order by p.hit, p.bookmarkCount desc";

        if (category != null) {
            jpql += " where p.category = :category" + orderBy;
        } else {
            jpql += orderBy;
        }

        return em.createQuery(jpql, Place.class).setParameter("category", category)
                .setFirstResult(0).setMaxResults(3).getResultList();
    }
}
