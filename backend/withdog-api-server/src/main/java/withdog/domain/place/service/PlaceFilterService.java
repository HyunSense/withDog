package withdog.domain.place.service;

import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.filter.FilterOption;
import withdog.domain.place.entity.filter.PlaceFilter;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PlaceFilterService {

    Set<PlaceFilter> findFilters(Long placeId);

    void initFilterOptions();

    int getFilterOptionId(String category, String value);

    List<FilterOption> getFilterOptions(List<Integer> filterOptionIds);

    Set<PlaceFilter> getPlaceFilters(Map<String, List<String>> filters, Place place);
}
