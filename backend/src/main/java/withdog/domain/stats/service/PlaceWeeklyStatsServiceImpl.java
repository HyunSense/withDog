package withdog.domain.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return placeWeeklyStatsRepository.findByPlaceAndWeekStartDate(place, weekMonday)
                .orElseGet(() -> placeWeeklyStatsRepository.save(
                        PlaceWeeklyStats.builder()
                                .place(place)
                                .weekStartDate(weekMonday)
                                .build()));
    }
}
