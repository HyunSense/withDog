package withdog.domain.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import withdog.domain.place.entity.PlaceImage;

@Repository
public interface PlaceImageRepository extends JpaRepository<PlaceImage, Long> {
}
