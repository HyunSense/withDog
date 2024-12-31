package withdog.domain.place.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class PlaceImageServiceImplTest {


    @Test
    @DisplayName("Stream으로 placeImage 가져오기")
    void getPlaceImage() {
        //given
        List<testObject> list = new ArrayList<>();
        list.add(new testObject(1L, "test1", 20));
        list.add(new testObject(2L, "test2", 30));
        list.add(new testObject(3L, "test3", 40));

        //when
        testObject testObject = list.stream().filter(l -> l.getId().equals(3L)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("test error"));

        //then
        Assertions.assertThat(testObject.getId()).isEqualTo(3L);
        System.out.println("testObject.getId() = " + testObject.getId());
    }

    public static class testObject {

        Long id;
        String name;
        int age;

        public testObject(Long id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}