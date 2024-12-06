package withdog.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import withdog.entity.Category;
import withdog.entity.Place;
import withdog.entity.PlaceImage;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private DynamicQueryPlaceRepository dynamicQueryPlaceRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    @DisplayName("페이징 테스트")
    void findAllWithPageable() {
        //given

        //when

        //then
    }


    @Test
    @DisplayName("인기 장소 3개 조회")
    void findTop3() {
        //given
        Category findCategory = categoryRepository.findById(1).get();
        String category = "camp";

        //when
        List<Place> places
                = dynamicQueryPlaceRepository.findTop3ByCategoryOrderByHitAndBookmarkCountDesc(findCategory);

        //then
        assertThat(places).size().isEqualTo(3);
        for (Place place : places) {
            System.out.println("place.getId() = " + place.getId());
            System.out.println("place.getName() = " + place.getName());
        }

    }

    @Test
    @DisplayName("Place join")
    void join() {

        Category findCategory = categoryRepository.findById(1).get();
        String category = "camp";
        Place place = placeRepository.findById(1L).get();

//        Place joinPlace = placeRepository.findByIdWithPlaceImagesAndPlaceBlogs(1L);

        System.out.println("place.getName() = " + place.getName());
//        System.out.println("joinPlace.getName() = " + joinPlace.getName());
//        System.out.println("joinPlace.getPlaceImages().get(0) = " + joinPlace.getPlaceImages().get(0).getName());

    }
}