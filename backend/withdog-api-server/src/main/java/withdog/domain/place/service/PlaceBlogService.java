package withdog.domain.place.service;

import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.PlaceBlog;

import java.util.List;

public interface PlaceBlogService {

    List<PlaceBlog> findBlogs(Long placeId);
    void save(Place place, List<String> blogUrls);
    void deleteAll(Place place);
}
