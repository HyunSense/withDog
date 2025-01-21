package withdog.domain.place.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import withdog.domain.place.entity.Place;

@SpringBootTest
class PlaceRepositoryDynamicTest {

    @Autowired
    PlaceRepository placeRepository;

    @Test
    @DisplayName("검색 keyword를 통한 장소 목록 조회")
    void findAllPlacesByTypeAndKeyword() {
        //given
        String type = "name";
//        String type = "area";

        //when
        Slice<Place> places = placeRepository.findAllPlacesByTypeAndKeyword(type, "드루왈", PageRequest.of(0, 10));

        //then
        places.getContent().forEach(p -> System.out.println(p.getName()));

    }

}