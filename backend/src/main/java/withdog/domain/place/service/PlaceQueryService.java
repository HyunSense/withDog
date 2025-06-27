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

/**
 * 장소 조회 전용 서비스
 * Caching을 통해 조회 성능 향상 목적
 */
@Service
@RequiredArgsConstructor
public class PlaceQueryService {

    private final PlaceWeeklyStatsServiceImpl placeWeeklyStatsService;
    private final PlaceRepository placeRepository;

    /**
     * 장소 상세 정보 조회 (캐시 전용)
     * @param place 조회할 Place 엔티티
     * @return 장소 상세 DTO
     */
    @Cacheable(value = "placeDetail", key = "#place.id")
    @Transactional(readOnly = true)
    public PlaceDetailResponseDto findPlaceCached(Place place) {

        List<PlaceImage> placeImages = place.getPlaceImages();
        List<PlaceBlog> placeBlogs = place.getPlaceBlogs();
        Set<PlaceFilter> placeFilters = place.getPlaceFilters();

        return PlaceDetailResponseDto.fromEntity(place, placeImages, placeBlogs, placeFilters);
    }

    /**
     * 인기 장소 Top 3 조회 (캐시 전용)
     * @return 인기 장소 DTO 리스트
     */
    @Cacheable(value = "place", key="'top3'")
    @Transactional(readOnly = true)
    public List<PlaceResponseDto> getTop3Cached() {

        // 하드코딩 수정 필요
        int pageNumber = 0;
        int pageSize = 3;

        List<PlaceWeeklyStats> placeWeeklyStatsList = placeWeeklyStatsService.getTop3PlaceWeeklyStats(PageRequest.of(pageNumber, pageSize));

        return PlaceResponseDto.fromEntityPlaceWeeklyStatsList(placeWeeklyStatsList);
    }

    /**
     * 최근 등록 장소 목록 조회 (캐시 전용)
     * @param limit 조회할 개수
     * @return 최근 장소 DTO 리스트
     */
    @Cacheable(value = "place", key="'recentPlaces'")
    @Transactional(readOnly = true)
    public List<PlaceResponseDto> recentPlacesCached(int limit) {

        PageRequest pageRequest = PageRequest.of(0, limit);
        List<Place> places = placeRepository.findByOrderByCreatedAtDesc(pageRequest);

        return PlaceResponseDto.fromEntityList(places);
    }

    /**
     * 랜덤 장소 목록 조회 (캐시 전용)
     * @param limit 조회할 개수
     * @return 랜덤 장소 DTO 리스트
     */
    @Cacheable(value = "place", key="'randomPlaces'")
    @Transactional(readOnly = true)
    public List<PlaceResponseDto> randomPlacesCached(int limit) {

        PageRequest pageRequest = PageRequest.of(0, limit);
        List<Place> places = placeRepository.findByOrderByRandom(pageRequest);

        return PlaceResponseDto.fromEntityList(places);
    }

    /**
     * 전체 장소 수 조회 (캐시 전용)
     * @return 전체 장소 수
     */
    @Cacheable(value = "placeCounts", key="'total'")
    @Transactional(readOnly = true)
    public long countPlacesCached() {
        return placeRepository.count();
    }

}
