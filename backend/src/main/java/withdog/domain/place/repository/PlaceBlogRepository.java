package withdog.domain.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import withdog.domain.place.entity.PlaceBlog;

@Repository
public interface PlaceBlogRepository extends JpaRepository<PlaceBlog, Long> {
}
