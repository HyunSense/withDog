package withdog.domain.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import withdog.domain.place.entity.PlaceBlog;

import java.util.List;

@Repository
public interface PlaceBlogRepository extends JpaRepository<PlaceBlog, Long> {

    List<PlaceBlog> findByPlaceId(Long placeId);
}
