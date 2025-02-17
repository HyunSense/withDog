package withdog.domain.place.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import withdog.domain.place.entity.Place;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    @DisplayName("Tpye별 장소 조회")
    void findPlacesByTypesTest() {
        //given
        List<String> types = List.of("camp", "pension", "cafe");
        PageRequest pageRequest = PageRequest.of(0, 100);

        //when
        List<Place> placesByTypes = placeRepository.findPlacesByTypes(types, pageRequest);

        //then
//        System.out.println("placesByTypes = " + placesByTypes.get(0).getName());
        System.out.println("placesByTypes.size() = " + placesByTypes.size());
        System.out.println("placesByTypes.get(0).getName() = " + placesByTypes.get(0).getName());
        System.out.println("placesByTypes.get(0).getAddressPart1() = " + placesByTypes.get(0).getAddressPart1());
        System.out.println("placesByTypes.get(0).getAddressPart2() = " + placesByTypes.get(0).getAddressPart2());
        System.out.println("placesByTypes.get(0).getThumbnailUrl() = " + placesByTypes.get(0).getThumbnailUrl());
        System.out.println("placesByTypes.get(0).getPrice() = " + placesByTypes.get(0).getPrice());
    }

    @Test
    @DisplayName("랜덤 장소 조회")
    void findPlaceRandomTest() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 5);

        //when
        List<Place> places = placeRepository.findByOrderByRandom(pageRequest);

        //then
        System.out.println("places.size() = " + places.size());
    }

}