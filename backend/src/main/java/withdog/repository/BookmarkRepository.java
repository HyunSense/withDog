package withdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import withdog.entity.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
