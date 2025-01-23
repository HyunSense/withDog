package withdog.domain.place.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import withdog.domain.place.entity.Place;


public interface PlaceRepositoryDynamic {

//    Slice<Place> findAllPlacesByTypeAndKeyword(String type, String keyword, Pageable pageable);

    Page<Place> findAllPlacesByTypeAndKeyword(String type, String keyword, Pageable pageable);
}
