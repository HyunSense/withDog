package withdog.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import withdog.stats.dto.PopularPlaceDto;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DailyStatRepositoryTest {

    @Autowired
    DailyStatRepository dailyStatRepository;


    @Test
    @DisplayName("인기장소 조회")
    void findPopularPlacesTest() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 10);
        LocalDate startDate = LocalDate.of(2025, 4, 17);
        LocalDate endDate = LocalDate.of(2025, 4, 21);

        //when
        List<PopularPlaceDto> popularPlaces = dailyStatRepository.findPopularPlaces(startDate, endDate, pageRequest);

        //then
        System.out.println("popularPlaces = " + popularPlaces);
    }


}