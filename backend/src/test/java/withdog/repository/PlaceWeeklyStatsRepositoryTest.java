package withdog.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import withdog.entity.Category;
import withdog.entity.Place;
import withdog.entity.PlaceWeeklyStats;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PlaceWeeklyStatsRepositoryTest {

    @Autowired
    private PlaceWeeklyStatsRepository placeWeeklyStatsRepository;

    @Test
    @DisplayName("인기 장소 상위 3개")
    void getTop3() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 3);

        //when
        List<PlaceWeeklyStats> top3 = placeWeeklyStatsRepository.findTop3(pageRequest);

        //then
        for (PlaceWeeklyStats placeWeeklyStats : top3) {
            System.out.println("placeWeeklyStats.getPlace().getName() = " + placeWeeklyStats.getPlace().getName());
        }
        assertThat(top3.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("카테고리별 인기 장소 상위 3개")
    void getTop3ByCategory() {
        //given
        Category category = Category.builder().id(1).name("camp").build();
        PageRequest pageRequest = PageRequest.of(0, 3);


        //when
        List<PlaceWeeklyStats> top3ByCategory = placeWeeklyStatsRepository.findTop3ByCategory(category, pageRequest);

        //then
        for (PlaceWeeklyStats placeWeeklyStats : top3ByCategory) {
            System.out.println("placeWeeklyStats.getPlace().getCategory().getName() = " + placeWeeklyStats.getPlace().getCategory().getName());
            System.out.println("placeWeeklyStats.getPlace().getName() = " + placeWeeklyStats.getPlace().getName());
        }
        assertThat(top3ByCategory.size()).isEqualTo(2);
    }
}