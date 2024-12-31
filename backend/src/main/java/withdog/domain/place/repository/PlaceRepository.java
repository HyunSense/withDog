package withdog.domain.place.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import withdog.domain.member.entity.Member;
import withdog.domain.place.entity.Place;

import java.util.List;
import java.util.Optional;
//TODO: 쿼리수정필요 spirng data jpa로 c.id x => c = category
@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    // List 일경우 distinct 생략 가능(hibernate6 부터 자동 적용)
    @Query("select distinct p from Place p join fetch p.placeImages i")
    List<Place> findAllWithPlaceImages();

    // 반환타입 Slice
    @Query("select p from Place p join fetch p.category c where c.id = :categoryId ")
    Slice<Place> findAllPlacesByCategoryId(int categoryId, Pageable pageable);

    @Query("select p from Place p join fetch p.category")
    Slice<Place> findAllPlaces(Pageable pageable);

    @Query("select distinct p from Place p left join fetch p.category left join fetch p.placeImages pi where p.id = :id order by pi.imagePosition")
    Optional<Place> findByIdWithCategoryAndPlaceImages(@Param("id") Long id);

//    @Query("select p from Place p left join fetch p.category left join fetch p.bookmarks b where b.member = :member")
//    List<Place> findAllByMemberBookmarkedPlaces(Member member);
}
