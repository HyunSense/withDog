package withdog.messaging.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import withdog.messaging.service.UserMessageProcessingService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatScheduler {

    private final UserMessageProcessingService userMessageProcessingService;

    @Value("${withdog.place.filters.names}")
    private List<String> filterNames;

    private final static String EVENT_TYPE_FILTERS = "filters";
    private final static String EVENT_TYPE_VIEWS = "views";
    private final static String EVENT_TYPE_BOOKMARKS = "bookmarks";


//    @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(cron = "*/30 * * * * *")
    public void calculatePlaceStat() {
        userMessageProcessingService.processPlaceStat();
    }

//    @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(cron = "*/30 * * * * *")
    public void performDailyStatsBackups() {
        for (String filterName : filterNames) {
            userMessageProcessingService.migrateDailyStatsFromRedisToDb(EVENT_TYPE_FILTERS, filterName);
        }

        userMessageProcessingService.migrateDailyStatsFromRedisToDb(EVENT_TYPE_VIEWS, null);
        userMessageProcessingService.migrateDailyStatsFromRedisToDb(EVENT_TYPE_BOOKMARKS, null);
    }
}
