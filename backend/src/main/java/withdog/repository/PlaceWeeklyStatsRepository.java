package withdog.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import withdog.entity.Category;
import withdog.entity.Place;
import withdog.entity.PlaceWeeklyStats;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceWeeklyStatsRepository extends JpaRepository<PlaceWeeklyStats, Long> {


    Optional<PlaceWeeklyStats> findByPlaceAndWeekStartDate(Place place, LocalDate weekStartDate);

    @Query("select pw from PlaceWeeklyStats pw join fetch pw.place p join fetch p.category c order by pw.hitCount desc, pw.bookmarkCount * 2 desc")
    List<PlaceWeeklyStats> findTop3(Pageable pageable);

    @Query("select pw from PlaceWeeklyStats pw join fetch pw.place p join fetch p.category c where p.category = :category order by pw.hitCount desc, pw.bookmarkCount * 2 desc")
    List<PlaceWeeklyStats> findTop3ByCategory(Category category, Pageable pageable);

}
