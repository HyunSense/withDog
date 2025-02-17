package withdog.domain.place.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import withdog.domain.place.entity.Place;

import java.util.List;


public interface PlaceRepositoryDynamic {

//    Slice<Place> findAllPlacesByTypeAndKeyword(String type, String keyword, Pageable pageable);

    Page<Place> findAllPlacesByTypeAndKeyword(String type, String keyword, Pageable pageable);

    List<Place> searchPlacesWithMultiFiltersUsingSubQuery();

//    Slice<Place> searchPlacesWithMultiFilters(String keyword, List<String> city, List<String> types,
//                                             List<String> petAccessTypes, List<String> petSizes,
//                                             List<String> services, int offset, int size, String sort);

    Slice<Place> searchPlacesWithMultiFilters(String keyword, List<String> city, List<String> types,
                                              List<String> petAccessTypes, List<String> petSizes,
                                              List<String> services, Pageable pageable);
}
