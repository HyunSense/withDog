package withdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import withdog.entity.Bookmark;
import withdog.entity.Member;
import withdog.entity.Place;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsByMemberAndPlace(Member member, Place place);
    Optional<Bookmark> findByMemberAndPlace(Member member, Place place);
    void deleteByMemberAndPlace(Member member, Place place);
}
