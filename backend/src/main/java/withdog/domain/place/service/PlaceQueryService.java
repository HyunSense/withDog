package withdog.domain.place.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.common.dto.response.SliceResponseDto;
import withdog.domain.place.dto.request.PlaceSearchRequestDto;
import withdog.domain.place.dto.response.PlaceDetailResponseDto;
import withdog.domain.place.dto.response.PlaceResponseDto;
import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.PlaceBlog;
import withdog.domain.place.entity.PlaceImage;
import withdog.domain.place.entity.filter.PlaceFilter;
import withdog.domain.place.repository.PlaceRepository;
import withdog.domain.stats.entity.PlaceWeeklyStats;
import withdog.domain.stats.service.PlaceWeeklyStatsServiceImpl;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlaceQueryService {

    private final PlaceWeeklyStatsServiceImpl placeWeeklyStatsService;
    private final PlaceRepository placeRepository;

    @Cacheable(value = "placeDetail", key = "#place.id")
    @Transactional(readOnly = true)
    public PlaceDetailResponseDto findPlaceCached(Place place) {

        List<PlaceImage> placeImages = place.getPlaceImages();
        List<PlaceBlog> placeBlogs = place.getPlaceBlogs();
        Set<PlaceFilter> placeFilters = place.getPlaceFilters();

        return PlaceDetailResponseDto.fromEntity(place, placeImages, placeBlogs, placeFilters);
    }

    @Cacheable(value = "place", key="'top3'")
    @Transactional(readOnly = true)
    public List<PlaceResponseDto> getTop3Cached() {

        // 하드코딩 수정 필요
        int pageNumber = 0;
        int pageSize = 3;

        List<PlaceWeeklyStats> placeWeeklyStatsList = placeWeeklyStatsService.getTop3PlaceWeeklyStats(PageRequest.of(pageNumber, pageSize));

        return PlaceResponseDto.fromEntityPlaceWeeklyStatsList(placeWeeklyStatsList);
    }

    @Cacheable(value = "place", key="'recentPlaces'")
    @Transactional(readOnly = true)
    public List<PlaceResponseDto> recentPlacesCached(int limit) {

        PageRequest pageRequest = PageRequest.of(0, limit);
        List<Place> places = placeRepository.findByOrderByCreatedAtDesc(pageRequest);

        return PlaceResponseDto.fromEntityList(places);
    }

    @Cacheable(value = "place", key="'randomPlaces'")
    @Transactional(readOnly = true)
    public List<PlaceResponseDto> randomPlacesCached(int limit) {

        PageRequest pageRequest = PageRequest.of(0, limit);
        List<Place> places = placeRepository.findByOrderByRandom(pageRequest);

        return PlaceResponseDto.fromEntityList(places);
    }

    @Cacheable(value = "placeCounts", key="'total'")
    @Transactional(readOnly = true)
    public long countPlacesCached() {
        return placeRepository.count();
    }

}
