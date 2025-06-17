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

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryDynamic{

    @Query("select p from Place p order by p.id desc")
    Slice<Place> findAllPlaces(Pageable pageable);

    @Query("select distinct p from Place p left join fetch p.placeImages pi where p.id = :id")
    Optional<Place> findByIdWithPlaceImages(Long id);

    @Query("select distinct p from Place p join p.placeFilters pf join pf.filterOption fo where fo.value in :types")
    List<Place> findPlacesByTypes(@Param("types") List<String> types, Pageable pageable);

    List<Place> findByOrderByCreatedAtDesc(Pageable pageable);

    @Query("select p from Place p order by rand()")
    List<Place> findByOrderByRandom(Pageable pageable);

    @Query("select distinct p from Place p " +
            "left join fetch p.placeBlogs pb " +
            "left join fetch p.placeFilters pf " +
            "left join fetch pf.filterOption fo " +
            "left join fetch fo.filterCategory fc where p.id = :id")
    Optional<Place> findByIdWithPlaceBlogsAndPlaceFilters(Long id);
}
