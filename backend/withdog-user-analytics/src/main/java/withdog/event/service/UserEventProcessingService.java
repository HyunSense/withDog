package withdog.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.event.model.place.PlaceActionEvent;
import withdog.event.model.place.PlaceFilterEvent;
import withdog.repository.AuditLogRepository;
import withdog.repository.DailyStatRepository;
import withdog.stats.dto.PopularPlaceDto;
import withdog.stats.entity.AuditLog;
import withdog.stats.entity.DailyStat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventProcessingService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${withdog.place.filters.names}")
    private final List<String> allowedFilters;

    @Value("${withdog.place.popular-places.page-size}")
    private int popularPlacesSize;

    private final DailyStatRepository dailyStatRepository;
    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void processFilterEvent(PlaceFilterEvent event) {

        LocalDate date = parseDate(event.getTimestamp());
        event.getFilters().forEach((filterName, values) -> {
            if (!allowedFilters.contains(filterName)) return;
            String redisKey = buildRedisKey(event.getEventType(), date, filterName);
            values.forEach(value ->
                    redisTemplate.opsForHash().increment(redisKey, value, 1));
        });
    }

    @Transactional
    public void processActionEvent(PlaceActionEvent event) {

        LocalDate date = parseDate(event.getTimestamp());
        String redisKey = buildRedisKey(event.getEventType(), date);

        redisTemplate.opsForHash().increment(redisKey, event.getPlaceId(), 1);
    }

    @Transactional
    public void migrateDailyStatsFromRedisToDb(String eventType, String metricKey) {

        LocalDate date = LocalDate.now().minusDays(1);
        log.info("Starting backup for date: {}, eventType: {}, metricKey: {}", date, eventType, metricKey);
        String taskName = eventType + "_backup";

        // Log backup 시작
        AuditLog startLog = AuditLog.builder()
                .taskName(taskName)
                .taskDate(date)
                .status("STARTED")
                .message("Backup initiated for " + eventType + (metricKey != null ? ":" + metricKey : ""))
                .build();

        auditLogRepository.save(startLog);

        try {
            String redisKey = buildRedisKey(eventType, date, metricKey);
            List<DailyStat> stats = convertRedisToDailyStats(redisKey, eventType, metricKey, date);
            saveDailyStatsWithAudit(stats, taskName, date);
        } catch (Exception e) {
            handleBackupFailure(taskName, date, e);
        }
    }

    public void calculatePopularPlaces() {

        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now().minusDays(1);

        List<PopularPlaceDto> popularPlaces = dailyStatRepository.findPopularPlaces(startDate, endDate, PageRequest.of(0, popularPlacesSize));

        for (PopularPlaceDto popularPlace : popularPlaces) {
            redisTemplate.opsForZSet().add("popular_places", Integer.valueOf(popularPlace.getPlaceId()), popularPlace.getPopularScore());
        }

    }


    private List<DailyStat> convertRedisToDailyStats(String redisKey, String eventType, String metricKey, LocalDate date) {

        Map<Object, Object> entries = redisTemplate.opsForHash().entries(redisKey);
        return entries.entrySet().stream()
                .filter(entry -> !dailyStatRepository.existsByEventTypeAndDateAndMetricKeyAndMetricValue(eventType, date, metricKey, String.valueOf(entry.getKey())))
                .map(entry -> DailyStat.builder()
                        .eventType(eventType)
                        .date(date)
                        .metricKey(metricKey)
                        .metricValue(String.valueOf(entry.getKey())) // hashKey
                        .count(Long.parseLong(String.valueOf(entry.getValue()))) // hashValue
                        .build()
                ).toList();
    }

    private void saveDailyStatsWithAudit(List<DailyStat> dailyStats, String taskName, LocalDate date) {

        if (!dailyStats.isEmpty()) {
            dailyStatRepository.saveAll(dailyStats);

            AuditLog successLog = AuditLog.builder()
                    .taskName(taskName)
                    .taskDate(date)
                    .status("SUCCESS")
                    .message("Backed up " + dailyStats.size() + " records")
                    .build();
            auditLogRepository.save(successLog);
        }
    }

    private void handleBackupFailure(String taskName, LocalDate date, Exception e) {
        String errorMessage = "Backup failed: " + e.getMessage();
        AuditLog failedLog = AuditLog.builder()
                .taskName(taskName)
                .taskDate(date)
                .status("FAILED")
                .message(errorMessage)
                .build();

        auditLogRepository.save(failedLog);
        log.error("Backup failed for: {} on: {}", taskName, date, e);
    }

    private LocalDate parseDate(Long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


    //TODO: NPE 체크
    private String buildRedisKey(String eventType, LocalDate date) {
        return String.format("%s:%s", eventType, date.format(DateTimeFormatter.BASIC_ISO_DATE));
    }

    private String buildRedisKey(String eventType, LocalDate date, String metricKey) {

        String baseKey = buildRedisKey(eventType, date);

        if (metricKey == null) {
            return baseKey;
        }
        return String.format("%s:%s", baseKey, metricKey);
    }
}
