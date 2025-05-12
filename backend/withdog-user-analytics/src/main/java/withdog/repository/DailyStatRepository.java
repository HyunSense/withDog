package withdog.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import withdog.stats.dto.PopularPlaceDto;
import withdog.stats.entity.DailyStat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface DailyStatRepository extends JpaRepository<DailyStat, Long> {
    //TODO: Method 복잡, 쿼리 개선 필요 count X, Native Query 사용 여부 체크 (Exists)
//    boolean existsByEventTypeAndDateAndMetricKeyAndMetricValue(String eventType, String date, String metricKey, String metricValue);
    boolean existsByEventTypeAndDateAndMetricKeyAndMetricValue(String eventType, LocalDate date, String metricKey, String metricValue);

    @Query("select new withdog.stats.dto.PopularPlaceDto(s.metricValue," +
            "sum(case when s.eventType = 'views' then s.count else 0 end)," +
            "sum(case when s.eventType = 'bookmarks' then s.count else 0 end)," +
            "sum(" +
            "case " +
            "when s.eventType = 'views' then s.count " +
            "when s.eventType = 'bookmarks' then s.count * 2 " +
            "else 0 " +
            "end)" +
            ") from DailyStat s " +
            "where s.eventType in ('views', 'bookmarks') " +
            "and s.date between :startDate and :endDate " +
            "group by s.metricValue")
    List<PopularPlaceDto> findPopularPlaces(LocalDate startDate, LocalDate endDate);
}


