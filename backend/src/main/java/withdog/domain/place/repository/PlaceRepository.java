package withdog.domain.place.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import withdog.domain.place.entity.Place;

import java.util.List;
import java.util.Optional;
//TODO: 쿼리수정필요 spirng data jpa로 c.id x => c = category
@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryDynamic{

    // List 일경우 distinct 생략 가능(hibernate6 부터 자동 적용)
    @Query("select distinct p from Place p join fetch p.placeImages i")
    List<Place> findAllWithPlaceImages();

    // 반환타입 Slice
    @Query("select p from Place p join fetch p.category c where c.id = :categoryId order by p.createdAt desc")
    Slice<Place> findAllPlacesByCategoryId(int categoryId, Pageable pageable);

    @Query("select p from Place p join fetch p.category order by p.createdAt desc")
    Slice<Place> findAllPlaces(Pageable pageable);

    @Query("select distinct p from Place p left join fetch p.category left join fetch p.placeImages pi where p.id = :id order by pi.imagePosition")
    Optional<Place> findByIdWithCategoryAndPlaceImages(@Param("id") Long id);

    @Query("select p from Place p join fetch p.placeBlogs pb where p.id = :id")
    Optional<Place> findByIdWithPlaceBlogs(Long id);

    @Query("select distinct p from Place p left join fetch p.placeBlogs pb left join fetch p.placeFilters pf left join fetch pf.filterOption fo left join fetch fo.filterCategory fc where p.id = :id")
    Optional<Place> findByIdWithPlaceBlogsAndPlaceFilters(Long id);

    @Query("select distinct p from Place p left join fetch p.placeImages pi where p.id = :id")
    Optional<Place> findByIdWithPlaceImages(Long id);

    @Query("select distinct p from Place p join p.placeFilters pf join pf.filterOption fo where fo.value = :type")
    Slice<Place> findPlacesByType(@Param("type") String type, Pageable pageable);

    @Query("select distinct p from Place p join p.placeFilters pf join pf.filterOption fo where fo.value in :types")
    List<Place> findPlacesByTypes(@Param("types") List<String> types, Pageable pageable);

    List<Place> findByOrderByCreatedAtDesc(Pageable pageable);

    @Query("select p from Place p order by rand()")
    List<Place> findByOrderByRandom(Pageable pageable);
}
