package withdog.domain.place.repository.filter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import withdog.domain.place.entity.filter.PlaceFilter;

@Repository
public interface PlaceFilterRepository extends JpaRepository<PlaceFilter, Integer> {
}
