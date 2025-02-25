package withdog.domain.place.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import withdog.domain.place.entity.Place;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PlaceRepositoryDynamicTest {

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    EntityManager em;

    private final static String KEYWORD = "keyword";
    private final static String CITY = "city";
    private final static String TYPES = "types";
    private final static String PET_ACCESS_TYPES = "petAccessTypes";
    private final static String PET_SIZES = "petSizes";
    private final static String SERVICES = "services";

//    @Test
    @DisplayName("subquery를 이용한 다중 필터 검색")
    void SearchPlacesWithMultiFiltersUsingSubQuery() {

        FilterRequestDto dto = new FilterRequestDto();
        dto.setKeyword("noImg");
        dto.setCity(Arrays.asList("인천", "서울"));
        dto.setTypes(Arrays.asList("pension", "camp"));
        dto.setPetAccessTypes(Arrays.asList("petOnly", "petFriendly"));
        dto.setPetSizes(Arrays.asList("sm", "md"));
        dto.setServices(Arrays.asList("parking", "outdoorFacilities"));

        System.out.println("dto = " + dto);

        StringBuilder jpql = new StringBuilder("select p from Place p ");

        if (dto != null) {
            jpql.append("where ");
        }

        boolean andFlag = false;

        if (dto.getKeyword() != null && !dto.getKeyword().isBlank()) {
            jpql.append("p.name like concat('%', :keyword, '%') ");
            andFlag = true;
        }

        if (dto.getCity() != null && !dto.getCity().isEmpty()) {

            if (andFlag) {
                jpql.append("and ");
            }
            jpql.append(
                    "exists (select 1 from p.placeFilters pf " +
                            "join pf.filterOption fo " +
                            "join fo.filterCategory fc " +
                            "where pf.place = p " +
                            "and fc.name = 'city' " +
                            "and fo.value in (:city) " +
                            ") "
            );
        }

        if (dto.getTypes() != null && !dto.getTypes().isEmpty()) {

            if (andFlag) {
                jpql.append("and ");
            }
            jpql.append(
                    "exists (select 1 from p.placeFilters pf " +
                            "join pf.filterOption fo " +
                            "join fo.filterCategory fc " +
                            "where pf.place = p " +
                            "and fc.name = 'types' " +
                            "and fo.value in (:types) " +
                            ") "
            );
        }

        if (dto.getPetAccessTypes() != null && !dto.getPetAccessTypes().isEmpty()) {
            if (andFlag) {
                jpql.append("and ");
            }
            jpql.append(
                    "exists (select 1 from p.placeFilters pf " +
                            "join pf.filterOption fo " +
                            "join fo.filterCategory fc " +
                            "where pf.place = p " +
                            "and fc.name = 'petAccessTypes' " +
                            "and fo.value in (:petAccessTypes) " +
                            ") "
            );
        }

        if (dto.getPetSizes() != null && !dto.getPetSizes().isEmpty()) {
            if (andFlag) {
                jpql.append("and ");
            }
            jpql.append(
                    "(select count(distinct fo.value) from p.placeFilters pf " +
                            "join pf.filterOption fo " +
                            "join fo.filterCategory fc " +
                            "where pf.place = p " +
                            "and fc.name = 'petSizes' " +
                            "and fo.value in (:petSizes) " +
                            ") = "
            );
            jpql.append(dto.getPetSizes().size());
            jpql.append(" ");
        }

        if (dto.getServices() != null && !dto.getServices().isEmpty()) {
            if (andFlag) {
                jpql.append("and ");
            }
            jpql.append(
                    "(select count(distinct fo.value) from p.placeFilters pf " +
                            "join pf.filterOption fo " +
                            "join fo.filterCategory fc " +
                            "where pf.place = p " +
                            "and fc.name = 'services' " +
                            "and fo.value in (:services) " +
                            ") = "

            );
            jpql.append(dto.getServices().size());
        }

        System.out.println("jpql.toString() = " + jpql);

        TypedQuery<Place> query = em.createQuery(jpql.toString(), Place.class);
        query.setParameter(KEYWORD, dto.getKeyword());
        query.setParameter(CITY, dto.getCity());
        query.setParameter(TYPES, dto.getTypes());
        query.setParameter(PET_ACCESS_TYPES, dto.getPetAccessTypes());
        query.setParameter(PET_SIZES, dto.getPetSizes());
        query.setParameter(SERVICES, dto.getServices());
        List<Place> places = query.getResultList();

        System.out.println("places.size() = " + places.size());
        for (Place place : places) {
            System.out.println("place.getName() = " + place.getName());
        }


//        String jpql2 = "select p from Place p where " +
//                "p.name like concat('%', :keyword, '%') " +
//                "and exists ( " + // [OR 조건] 지역 (인천 OR 서울)
//                "select 1 from p.placeFilters pf " +
//                "join pf.filterOption fo " +
//                "join fo.filterCategory fc " +
//                "where pf.place = p " +
//                "and fc.name = 'city' " +
//                "and fo.value in ('인천', '서울') " +
//                ") " +
//                "and exists ( " + // [OR 조건] 유형 (펜션 OR 캠핑)
//                "select 1 from p.placeFilters pf " +
//                "join pf.filterOption fo " +
//                "join fo.filterCategory fc " +
//                "where pf.place = p " +
//                "and fc.name = 'types' " +
//                "and fo.value in ('pension', 'camp') " +
//                ") " +
//                "and exists ( " + // [OR 조건] 반려견 이용 유형 (전용 OR 동반)
//                "select 1 from p.placeFilters pf " +
//                "join pf.filterOption fo " +
//                "join fo.filterCategory fc " +
//                "where pf.place = p " +
//                "and fc.name = 'petAccessTypes' " +
//                "and fo.value in ('petOnly', 'petFriendly') " +
//                ") " +
//                "and ( " + // [AND 조건] 허용견 크기 (소형견 AND 중형견)
//                "select count(distinct fo.value) from p.placeFilters pf " +
//                "join pf.filterOption fo " +
//                "join fo.filterCategory fc " +
//                "where pf.place = p " +
//                "and fc.name = 'petSizes' " +
//                "and fo.value in ('sm', 'md') " +
//                ") = 2 " +
//                "and ( " + // [AND 조건] 편의시설 (주차장 AND 야외시설)
//                "select count(distinct fo.value) from p.placeFilters pf " +
//                "join pf.filterOption fo " +
//                "join fo.filterCategory fc " +
//                "where pf.place = p " +
//                "and fc.name = 'services' " +
//                "and fo.value in ('parking', 'outdoorFacilities') " +
//                ") = 2 ";
//
//        TypedQuery<Place> query = em.createQuery(jpql2, Place.class);
//        query.setParameter("keyword", "noImg");
//        List<Place> resultList = query.getResultList();
//
//        assertThat(resultList.size()).isEqualTo(1);
//        for (Place place : resultList) {
//            System.out.println("place.getName() = " + place.getName());
//        }
    }
    
    @Test
    @DisplayName("리펙토링된 다중 필터 검색")
    void refactoredSearchPlacesWithMultiFilters() {

        FilterRequestDto dto = new FilterRequestDto();
        dto.setKeyword("noImg");
        dto.setCity(Arrays.asList("인천", "서울"));
        dto.setTypes(Arrays.asList("pension", "camp"));
        dto.setPetAccessTypes(Arrays.asList("petOnly", "petFriendly"));
        dto.setPetSizes(Arrays.asList("sm", "md"));
        dto.setServices(Arrays.asList("parking", "outdoorFacilities"));

        // 1. 쿼리 빌더 생성
        DynamicFilterQueryBuilderTest queryBuilder = new DynamicFilterQueryBuilderTest();

        queryBuilder
                .baseQuery()
                .withKeyword(dto.getKeyword())
                .withOrFilter("city", dto.getCity())
                .withOrFilter("types", dto.getTypes())
                .withOrFilter("petAccessTypes", dto.getPetAccessTypes())
                .withAndFilter("petSizes", dto.getPetSizes())
                .withAndFilter("services", dto.getServices())
                .withPageNation(0, 10);

        // 2. 쿼리 실행
        TypedQuery<Place> query = em.createQuery(queryBuilder.getJpql(), Place.class);
        queryBuilder.getParams().forEach((key, value) -> query.setParameter(key, value));
        List<Place> places = query.getResultList();
        System.out.println("places.size() = " + places.size());
        for (Place place : places) {
            System.out.println("place.getName() = " + place.getName());
        }
    }

    @Getter @Setter
    @ToString
    static class FilterRequestDto {

        String keyword;
        List<String> types;
        List<String> city;
        List<String> petAccessTypes;
        List<String> petSizes;
        List<String> services;

    }
}