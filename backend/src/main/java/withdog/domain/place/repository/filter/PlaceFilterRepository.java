package withdog.domain.place.repository.filter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import withdog.domain.place.entity.filter.PlaceFilter;

import java.util.Set;

@Repository
public interface PlaceFilterRepository extends JpaRepository<PlaceFilter, Long> {

    @Query("select pf from PlaceFilter pf join fetch pf.filterOption fo join fetch fo.filterCategory where pf.place.id = :placeId")
    Set<PlaceFilter> findByPlaceId(Long placeId);
}
