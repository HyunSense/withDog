package withdog.domain.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.domain.place.entity.Place;
import withdog.domain.stats.entity.PlaceWeeklyStats;
import withdog.domain.stats.repository.PlaceWeeklyStatsRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PlaceWeeklyStatsServiceImpl implements PlaceWeeklyStatsService {

    private final PlaceWeeklyStatsRepository placeWeeklyStatsRepository;


    @Override
    public void increaseBookmarkCount(Place place) {

        PlaceWeeklyStats placeWeeklyStats = getOrCreatePlaceWeeklyStats(place);
        placeWeeklyStats.increaseBookmarkCount();
    }

    @Override
    public void decreaseBookmarkCount(Place place) {
        PlaceWeeklyStats placeWeeklyStats = getOrCreatePlaceWeeklyStats(place);
        placeWeeklyStats.decreaseBookmarkCount();
    }

    @Override
    public void increaseHitCount(Place place) {
        PlaceWeeklyStats placeWeeklyStats = getOrCreatePlaceWeeklyStats(place);
        placeWeeklyStats.increaseHitCount();
    }

    @Override
    public List<PlaceWeeklyStats> getTop3PlaceWeeklyStats(Pageable pageable) {

        return placeWeeklyStatsRepository.findTop3(pageable);
    }

    private PlaceWeeklyStats getOrCreatePlaceWeeklyStats(Place place) {

        LocalDate weekMonday = LocalDate.now().with(DayOfWeek.MONDAY);

        try {
            return placeWeeklyStatsRepository.findByPlaceAndWeekStartDate(place, weekMonday)
                .orElseGet(() -> placeWeeklyStatsRepository.save(
                        PlaceWeeklyStats.builder()
                                .place(place)
                                .weekStartDate(weekMonday)
                                .build()));
        } catch (DataIntegrityViolationException e) { // 동시성 문제 처리를 위한 catch 문
            // 누군가 이미 저장한 경우 → 재조회
            log.info("PlaceWeeklyStats already exists.");
            return placeWeeklyStatsRepository.findByPlaceAndWeekStartDate(place, weekMonday)
                    .orElseThrow();
        }
    }
}
