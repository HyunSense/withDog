package withdog.domain.place.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.common.constant.ApiResponseCode;
import withdog.common.exception.CustomException;
import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.filter.FilterOption;
import withdog.domain.place.entity.filter.PlaceFilter;
import withdog.domain.place.repository.filter.FilterOptionRepository;
import withdog.domain.place.repository.filter.PlaceFilterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class PlaceFilterServiceImpl implements PlaceFilterService {

    private final Map<String, Integer> filterOptionCache = new ConcurrentHashMap<>();
    private final PlaceFilterRepository placeFilterRepository;
    private final FilterOptionRepository filterOptionRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public PlaceFilterServiceImpl(FilterOptionRepository filterOptionRepository,  PlaceFilterRepository placeFilterRepository, ObjectMapper objectMapper) {
        this.filterOptionRepository = filterOptionRepository;
        this.placeFilterRepository = placeFilterRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    @Override
    public void initFilterOptions() {

        List<FilterOption> filterOptions = filterOptionRepository.findAllFilterOptions();
        filterOptions.forEach(fo
                -> filterOptionCache.put(fo.getFilterCategory().getName() + ":" + fo.getValue(), fo.getId()));

        filterOptionCache.forEach((key, value) -> log.info("key: [{}] value: [{}]", key, value));
    }

    @Override
    public Set<PlaceFilter> findFilters(Long placeId) {

        return placeFilterRepository.findByPlaceId(placeId);
    }

    @Override
    public int getFilterOptionId(String category, String value) {

        return filterOptionCache.getOrDefault(category + ":" + value, -1);
    }


    @Override
    public List<FilterOption> getFilterOptions(List<Integer> filterOptionIds) {

        return filterOptionRepository.findAllById(filterOptionIds);
    }

    @Override
    public Set<PlaceFilter> getPlaceFilters(Map<String, List<String>> filters, Place place) {

        List<Integer> filterOptionIds = new ArrayList<>();

        filters.forEach((key, values) -> {
            List<Integer> optionIdsByType =
                    values.stream().map(value -> getFilterOptionId(key, value))
                            .filter(id -> id != -1)
                            .collect(Collectors.toList());

            if (optionIdsByType.size() < values.size()) {
                log.warn("일부 필터 값이 유효하지 않음 - Category: {}", key);
            }
            filterOptionIds.addAll(optionIdsByType);
        });

        if (filterOptionIds.isEmpty()) {
            throw new CustomException(ApiResponseCode.SERVER_ERROR, "유효한 필터가 없습니다.");
        }

        List<FilterOption> filterOptions = getFilterOptions(filterOptionIds);
        Set<PlaceFilter> placeFilters =
                filterOptions.stream().map(fo -> new PlaceFilter(place, fo)).collect(Collectors.toSet());

        return placeFilters;
    }
}
