package withdog.domain.place.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import withdog.domain.place.entity.Place;

import java.util.List;


public interface PlaceRepositoryDynamic {

    Slice<Place> searchPlacesWithMultiFilters(String keyword, List<String> city, List<String> types,
                                              List<String> petAccessTypes, List<String> petSizes,
                                              List<String> services, Pageable pageable);

    Long getSearchPlacesTotalCount(String keyword, List<String> city, List<String> types,
                                   List<String> petAccessTypes, List<String> petSizes,
                                   List<String> services);
}
