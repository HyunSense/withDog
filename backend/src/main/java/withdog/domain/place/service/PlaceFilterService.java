package withdog.domain.place.service;

import withdog.domain.place.entity.Place;

import java.util.List;

public interface PlaceFilterService {

    void save(Place place, List<Integer> filterId);

}
