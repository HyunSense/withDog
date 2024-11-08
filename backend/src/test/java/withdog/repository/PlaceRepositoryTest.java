package withdog.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import withdog.entity.Place;
import withdog.entity.PlaceImage;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;


    @Test
    @DisplayName("페이징 테스트")
    void findAllWithPageable() {
        //given

        //when

        //then
    }

}