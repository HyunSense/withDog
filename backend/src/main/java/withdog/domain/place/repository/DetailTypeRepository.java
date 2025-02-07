package withdog.domain.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import withdog.domain.place.entity.DetailType;

@Repository
public interface DetailTypeRepository extends JpaRepository<DetailType, Integer> {
}
