package withdog.domain.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import withdog.domain.bookmark.entity.Bookmark;
import withdog.domain.member.entity.Member;
import withdog.domain.place.entity.Place;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsByMemberAndPlace(Member member, Place place);
    void deleteByMemberAndPlace(Member member, Place place);

    // select 제외 DML(insert, update, delete) 수행시 사용되는 어노테이션
    // @Query와 함께 사용되며 벌크연산을 할때 사용한다.
    // clearAutomatically = true 를 함께사용한다.
    // true로 설정하면 쿼리실행전 영속성 컨텍스트를 비우게된다.
    // false로 할 경우, 1차 캐시에있는 엔티티를 사용하게되어 변경된 데이터를 조회(감지)할 수 없게된다.
    // ex) select -> delete -> insert 를 하게된다면 insert시 pk, unique key 중복이 발생 가능성 있음
    @Modifying
    @Query("delete from Bookmark b where b.member = :member and b.place in :places")
    void deleteAllByMemberAndPlaces(Member member, List<Place> places);

    @Query("select b.place from Bookmark b join fetch b.place.category where b.member = :member")
    List<Place> findAllBookmarkedPlacesByMember(Member member);
}
