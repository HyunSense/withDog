package withdog.domain.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.common.constant.ApiResponseCode;
import withdog.common.exception.CustomException;
import withdog.domain.place.entity.Place;
import withdog.domain.place.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;


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
    public List<PlaceWeeklyStats> getTop3PlaceWeeklyStats(int categoryId, Pageable pageable) {

        if (categoryId != 0 && !categoryRepository.existsById(categoryId)) {
            throw new CustomException(ApiResponseCode.NOT_EXIST_CATEGORY);
        }

        if (categoryId == 0) {
            return placeWeeklyStatsRepository.findTop3(pageable);
        }
        return placeWeeklyStatsRepository.findTop3ByCategoryId(categoryId, pageable);
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
