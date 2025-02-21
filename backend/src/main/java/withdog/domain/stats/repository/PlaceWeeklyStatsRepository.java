package withdog.domain.stats.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import withdog.domain.place.entity.Place;
import withdog.domain.stats.entity.PlaceWeeklyStats;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceWeeklyStatsRepository extends JpaRepository<PlaceWeeklyStats, Long> {


    Optional<PlaceWeeklyStats> findByPlaceAndWeekStartDate(Place place, LocalDate weekStartDate);

    @Query("select pw from PlaceWeeklyStats pw join fetch pw.place p " +
            "where pw.weekStartDate = (select max(pw.weekStartDate) from PlaceWeeklyStats pw) " +
            "order by pw.bookmarkCount * 2 desc, pw.hitCount desc, p.id desc")
    List<PlaceWeeklyStats> findTop3(Pageable pageable);
}
