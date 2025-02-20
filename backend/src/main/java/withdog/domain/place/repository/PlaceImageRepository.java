package withdog.domain.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import withdog.domain.place.entity.PlaceImage;

import java.util.List;

@Repository
public interface PlaceImageRepository extends JpaRepository<PlaceImage, Long> {

    List<PlaceImage> findByPlaceId(Long placeId);
}
