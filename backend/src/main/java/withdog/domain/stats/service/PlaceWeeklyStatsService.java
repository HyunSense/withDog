package withdog.domain.stats.service;

import org.springframework.data.domain.Pageable;
import withdog.domain.place.entity.Category;
import withdog.domain.place.entity.Place;
import withdog.domain.stats.entity.PlaceWeeklyStats;

import java.util.List;

public interface PlaceWeeklyStatsService {

    void increaseBookmarkCount(Place place);
    void decreaseBookmarkCount(Place place);
    void increaseHitCount(Place place);
    List<PlaceWeeklyStats> getTop3PlaceWeeklyStats(Category category, Pageable pageable);
}
