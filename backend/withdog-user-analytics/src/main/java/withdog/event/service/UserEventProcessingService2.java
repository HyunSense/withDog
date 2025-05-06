//package withdog.event.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import withdog.event.model.place.PlaceBookmarkEvent;
//import withdog.event.model.place.PlaceDetailViewEvent;
//import withdog.event.model.place.UserSearchEvent;
//import withdog.repository.AuditLogRepository;
//import withdog.repository.DailyStatRepository;
//import withdog.stats.dto.PopularPlaceDto;
//import withdog.stats.entity.AuditLog;
//import withdog.stats.entity.DailyStat;
//
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class UserEventProcessingService2 {
//
//    private final RedisTemplate<String, Object> redisTemplate;
//    private final AuditLogRepository auditLogRepository;
//    private final DailyStatRepository dailyStatRepository;
//
//    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
//    private static final String REDIS_KEY_SEPARATOR = ":";
//
//    // TODO: EventType 하드 코딩 수정 필요
//    private static final String EVENT_TYPE_FILTERS = "filters";
//    private static final String EVENT_TYPE_VIEWS = "views";
//    private static final String EVENT_TYPE_BOOKMARKS = "bookmarks";
//
//    @Value("${withdog.place.filters.names}")
//    private List<String> filterNames;
//
//    @Value("${withdog.place.popular-places.page-size}")
//    private int popularPlacesSize;
//
//    // TODO: 필터 정적 코딩 수정 필요
////    private final static Set<String> FILTER_NAMES = Set.of("petAccessTypes", "petSizes", "city", "services", "types");
//
//    @Transactional
//    public void processUserPlaceSearchEvent(String key, UserSearchEvent event) {
//
//        LocalDate date = getFormattedDate(event.getTimestamp());
//        event.getFilters().forEach((filterName, filterValues) -> {
//            String redisKey = buildRedisKey(EVENT_TYPE_FILTERS, date.format(DATE_FORMATTER), filterName);
//            filterValues.forEach(value -> redisTemplate.opsForHash().increment(redisKey, value, 1));
//        });
//    }
//
//    @Transactional
//    public void processUserPlaceDetailViewEvent(String key, PlaceDetailViewEvent event) {
//
//        LocalDate date = getFormattedDate(event.getTimestamp());
//        String redisKey = buildRedisKey(EVENT_TYPE_VIEWS, date.format(DATE_FORMATTER));
//        String placeId = String.valueOf(event.getPlaceId());
//
//        redisTemplate.opsForHash().increment(redisKey, placeId, 1);
//    }
//
//    @Transactional
//    public void processUserPlaceBookmarkEvent(String key, PlaceBookmarkEvent event) {
//        // 집계 목적에 따른 증가만 처리
//        LocalDate date = getFormattedDate(event.getTimestamp());
//        String redisKey = buildRedisKey(EVENT_TYPE_BOOKMARKS, date.format(DATE_FORMATTER));
//        String placeId = String.valueOf(event.getPlaceId());
//
//        redisTemplate.opsForHash().increment(redisKey, placeId, 1);
//    }
//
//    @Scheduled(cron = "${withdog.scheduling.popular-places-cron}")
//    @Transactional(readOnly = true)
//    public void calculatePopularPlaces() {
//
//        LocalDate startDate = LocalDate.now().minusDays(7);
//        LocalDate endDate = LocalDate.now().minusDays(1);
//
//        List<PopularPlaceDto> popularPlaces = dailyStatRepository.findPopularPlaces(startDate, endDate, PageRequest.of(0, popularPlacesSize));
//
//        for (PopularPlaceDto popularPlace : popularPlaces) {
//            redisTemplate.opsForZSet().add("popular_places", Integer.valueOf(popularPlace.getPlaceId()), popularPlace.getPopularScore());
//        }
//    }
//
//    @Scheduled(cron = "${withdog.scheduling.daily-stats-backup-cron}")
//    public void performDailyStatsBackups() {
//        for (String filterName : filterNames) {
//            backupDailyStats(EVENT_TYPE_FILTERS, filterName);
//        }
//        backupDailyStats(EVENT_TYPE_VIEWS, null);
//        backupDailyStats(EVENT_TYPE_BOOKMARKS, null);
//    }
//
//    public void backupDailyStats(String eventType, String metricKey) {
//        LocalDate date = LocalDate.now().minusDays(1);
//        log.info("Starting backup for date: {}, eventType: {}, metricKey: {}", date, eventType, metricKey);
//        String taskName = eventType + "_backup";
//
//        // Log backup 시작
//        AuditLog startLog = AuditLog.builder()
//                .taskName(taskName)
//                .taskDate(date)
//                .status("STARTED")
//                .message("Backup initiated for " + eventType + (metricKey != null ? ":" + metricKey : ""))
//                .build();
//
//        auditLogRepository.save(startLog);
//
//        //Redis로 부터 data 조회
//        try {
//            String redisKey = metricKey != null ? buildRedisKey(eventType, date.format(DATE_FORMATTER), metricKey) : buildRedisKey(eventType, date.format(DATE_FORMATTER));
//
//            Map<Object, Object> entries = redisTemplate.opsForHash().entries(redisKey);
//
//            List<DailyStat> statList = new ArrayList<>();
//
//            for (Map.Entry<Object, Object> entry : entries.entrySet()) {
//                String value = String.valueOf(entry.getKey()); // hashKey
//                long count = Long.parseLong(String.valueOf(entry.getValue())); // hashValue
//
//                boolean exists = dailyStatRepository.existsByEventTypeAndDateAndMetricKeyAndMetricValue(eventType, date, metricKey, value);
//
//                if (!exists) {
//                    DailyStat stat = DailyStat.builder()
//                            .eventType(eventType)
//                            .date(date)
//                            .metricKey(metricKey)
//                            .metricValue(value)
//                            .count(count)
//                            .build();
//
//                    statList.add(stat);
//                }
//            }
//
//            if (!statList.isEmpty()) {
//                dailyStatRepository.saveAll(statList);
//            }
//
//            AuditLog successLog = AuditLog.builder()
//                    .taskName(taskName)
//                    .taskDate(date)
//                    .status("SUCCESS")
//                    .message("Backed up " + statList.size() + " records")
//                    .build();
//
//            auditLogRepository.save(successLog);
//            log.info("Backup completed for {}, processed {} records", redisKey, statList.size());
//
//        } catch (Exception e) {
//            String errorMessage = "Backup failed: " + e.getMessage();
//            AuditLog failedLog = AuditLog.builder()
//                    .taskName(taskName)
//                    .taskDate(date)
//                    .status("FAILED")
//                    .message(errorMessage)
//                    .build();
//
//            auditLogRepository.save(failedLog);
//            log.error("Backup failed for: {} on: {}", eventType, date, e);
//        }
//    }
//
//    private LocalDate getFormattedDate(Long timestamp) {
//        // UTC -> 시스템 시간대로 변경
//        return Instant.ofEpochMilli(timestamp)
//                .atZone(ZoneId.systemDefault())
//                .toLocalDate();
//    }
//
//    private String buildRedisKey(String... parts) {
//        return String.join(REDIS_KEY_SEPARATOR, parts);
//    }
//}
