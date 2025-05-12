package withdog.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import withdog.stats.dto.PopularPlaceDto;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class DailyStatRepositoryTest {

    @Autowired
    DailyStatRepository dailyStatRepository;


    @Test
    @DisplayName("인기장소 조회")
    void findPopularPlacesTest() {
        //given
        LocalDate startDate = LocalDate.of(2025, 5, 3);
        LocalDate endDate = LocalDate.of(2025, 5, 7);

        //when
        List<PopularPlaceDto> popularPlaces = dailyStatRepository.findPopularPlaces(startDate, endDate);

        //then
        System.out.println("popularPlaces = " + popularPlaces);
    }
}