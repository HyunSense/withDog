package withdog.domain.place.service;

import withdog.domain.place.entity.Place;

import java.util.List;

public interface PlaceBlogService {

    void save(Place place, List<String> blogUrls);
    void deleteAll(Place place);
}
