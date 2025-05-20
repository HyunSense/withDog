package withdog.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import withdog.domain.place.entity.Place;
import withdog.domain.place.repository.PlaceRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void insertDummyPlaces() {

        List<Place> placeList = new ArrayList<>();
        for (int i = 1000; i < 5000; i++) {
            Place place = Place.builder()
                    .name("test name-" + (i + 1))
                    .thumbnailUrl("test thumbnail-" + (i + 1))
                    .price(0)
                    .addressPart1("test addressPart1-" + (i + 1))
                    .addressPart2("test addressPart2-" + (i + 1))
                    .phone("test phone-" + (i + 1))
                    .reservationUrl("test reservationUrl-" + (i + 1))
                    .build();
            placeList.add(place);
        }
        placeRepository.saveAll(placeList);
    }
}
