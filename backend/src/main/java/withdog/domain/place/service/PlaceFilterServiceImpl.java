package withdog.domain.place.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.filter.PlaceFilter;
import withdog.domain.place.repository.filter.FilterOptionRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PlaceFilterServiceImpl implements PlaceFilterService {

    private FilterOptionRepository filterOptionRepository;

    @Override
    public void save(Place place, List<Integer> filterOptionIds) {
        List<PlaceFilter> placeFilters = place.getPlaceFilters();

    }
}
