package withdog.messaging.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.messaging.model.place.PlaceActionMessage;
import withdog.messaging.model.place.PlaceFilterMessage;
import withdog.messaging.util.constant.KafkaTopics;
import withdog.repository.AuditLogRepository;
import withdog.repository.DailyStatRepository;
import withdog.stats.dto.PlaceStatDto;
import withdog.stats.entity.AuditLog;
import withdog.stats.entity.DailyStat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserMessageProcessingService {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${withdog.place.filters.names}")
    private final List<String> allowedFilters;
    private final DailyStatRepository dailyStatRepository;
    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void processFilterEvent(PlaceFilterMessage event) {

        LocalDate date = parseDate(event.getTimestamp());
        event.getFilters().forEach((filterName, values) -> {
            if (!allowedFilters.contains(filterName)) return;
            String redisKey = buildRedisKey(event.getEventType(), date, filterName);
            values.forEach(value ->
                    redisTemplate.opsForHash().increment(redisKey, value, 1));
            redisTemplate.expire(redisKey, 24, TimeUnit.HOURS);
        });

    }

    @Transactional
    public void processActionEvent(PlaceActionMessage event) {

        LocalDate date = parseDate(event.getTimestamp());
        String redisKey = buildRedisKey(event.getEventType(), date);

        redisTemplate.opsForHash().increment(redisKey, event.getPlaceId().toString(), 1);
        redisTemplate.expire(redisKey, 24, TimeUnit.HOURS);
    }

    @Transactional
    public void processPlaceStat() {

        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now().minusDays(1);

        // 테스트용
//        LocalDate endDate = LocalDate.now();

        List<PlaceStatDto> placeStatDtos = dailyStatRepository.findPopularPlaces(startDate, endDate);

        for (PlaceStatDto placeStat : placeStatDtos) {
            redisTemplate.opsForZSet().add("popular_places", placeStat.getPlaceId(), placeStat.getPopularityScore());
        }
        redisTemplate.expire("popular_places", 24, TimeUnit.HOURS);
    }

    @Transactional
    public void migrateDailyStatsFromRedisToDb(String eventType, String metricKey) {

        LocalDate date = LocalDate.now().minusDays(1);
        // 테스트용
//        LocalDate date = LocalDate.now();
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