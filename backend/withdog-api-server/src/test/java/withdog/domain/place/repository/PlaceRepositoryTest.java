package withdog.domain.place.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.filter.PlaceFilter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    EntityManager em;

//    @Test
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

//    @Test
    @DisplayName("랜덤 장소 조회")
    void findPlaceRandomTest() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 5);

        //when
        List<Place> places = placeRepository.findByOrderByRandom(pageRequest);

        //then
        System.out.println("places.size() = " + places.size());
    }

    @Transactional
//    @Test
    @DisplayName("2개 이상의 컬렉션 조회, stream 그룹 매핑")
    void findByIdWithPlaceFiltersAndPlaceImagesAndPlaceBlogsTest() {
        //given

        //when
        Place place = placeRepository.findByIdWithPlaceBlogsAndPlaceFilters(107L).orElseThrow();
        //then
        System.out.println("place.getName() = " + place.getName());

        Set<PlaceFilter> placeFilters = place.getPlaceFilters();

        // filterOptions 에는 filter_category_id 와 value 가들어있음
        // filterCategory 에는 filter name이 들어있음
        // 그룹핑을 하려면 filter name내에 value로 그룹핑을 하여야함
        // placeFilters -> placeOption -> placeCategory


        Map<String, List<String>> collect =
                placeFilters.stream()
                        .collect(Collectors.groupingBy(pf -> pf.getFilterOption().getFilterCategory().getName(),
                                Collectors.mapping(pf -> pf.getFilterOption().getValue(), Collectors.toList())));

        System.out.println("collect = " + collect);

//        System.out.println("map = " + collect);
//        for (PlaceFilter placeFilter : place.getPlaceFilters()) {
//            System.out.println("placeFilter.getId() = " + placeFilter.getId());
//        }
//
//        for (PlaceFilter placeFilter : place.getPlaceFilters()) {
//            System.out.println("placeFilter.getFilterOption().getValue() = " + placeFilter.getFilterOption().getValue());
//        }
    }

}