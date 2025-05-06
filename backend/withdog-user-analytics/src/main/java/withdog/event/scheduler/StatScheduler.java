package withdog.event.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import withdog.event.service.UserEventProcessingService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatScheduler {

    private final UserEventProcessingService userEventProcessingService;

    @Value("${withdog.place.filters.names}")
    private List<String> filterNames;

    private final static String EVENT_TYPE_FILTERS = "filters";
    private final static String EVENT_TYPE_VIEWS = "views";
    private final static String EVENT_TYPE_BOOKMARKS = "bookmarks";


    @Scheduled(cron = "${withdog.scheduling.popular-places-cron}")
    public void calculatePopularPlaces() {
        userEventProcessingService.calculatePopularPlaces();
    }

    @Scheduled(cron = "${withdog.scheduling.daily-stats-backup-cron}")
    public void performDailyStatsBackups() {
        for (String filterName : filterNames) {
            userEventProcessingService.migrateDailyStatsFromRedisToDb(EVENT_TYPE_FILTERS, filterName);
        }

        userEventProcessingService.migrateDailyStatsFromRedisToDb(EVENT_TYPE_VIEWS, null);
        userEventProcessingService.migrateDailyStatsFromRedisToDb(EVENT_TYPE_BOOKMARKS, null);
    }
}
