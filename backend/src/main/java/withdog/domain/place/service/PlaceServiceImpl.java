package withdog.domain.place.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.common.constant.ApiResponseCode;
import withdog.common.dto.SliceInfo;
import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.common.dto.response.SliceResponseDto;
import withdog.common.exception.CustomException;
import withdog.domain.place.dto.PlaceNewImageDto;
import withdog.domain.place.dto.PlaceUpdateImagesDto;
import withdog.domain.place.dto.request.PlaceFormRequestDto;
import withdog.domain.place.dto.request.PlaceFormUpdateRequestDto;
import withdog.domain.place.dto.request.PlaceSearchRequestDto;
import withdog.domain.place.dto.response.PlaceDetailResponseDto;
import withdog.domain.place.dto.response.PlaceResponseDto;
import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.PlaceBlog;
import withdog.domain.place.entity.PlaceImage;
import withdog.domain.place.entity.filter.PlaceFilter;
import withdog.domain.place.repository.PlaceRepository;
import withdog.domain.stats.entity.PlaceWeeklyStats;
import withdog.domain.stats.service.PlaceWeeklyStatsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceWeeklyStatsService placeWeeklyStatsService;
    private final PlaceImageService placeImageService;
    private final PlaceBlogService placeBlogService;
    private final PlaceFilterService placeFilterService;

    private final CacheManager cacheManager;
    private final PlaceQueryService placeQueryService;

    @Transactional(readOnly = true)
    @Override
    public DataResponseDto<SliceResponseDto<PlaceResponseDto>> findAllPlace(Pageable pageable) {

        Slice<Place> slicePlaces = placeRepository.findAllPlaces(pageable);

        List<PlaceResponseDto> placeResponseDto = PlaceResponseDto.fromEntityList(slicePlaces.getContent());
        SliceResponseDto<PlaceResponseDto> responseDtos = toSliceResponse(slicePlaces, placeResponseDto);

        return DataResponseDto.success(responseDtos);
    }

    @Override
    public DataResponseDto<PlaceDetailResponseDto> findPlace(Long id) {

        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

        placeWeeklyStatsService.increaseHitCount(place);

        PlaceDetailResponseDto dto = placeQueryService.findPlaceCached(place);

        return DataResponseDto.success(dto);
    }

    //TODO: ADMIN 용 조회 메서드 수정 필요
    @Transactional(readOnly = true)
    @Override
    public DataResponseDto<PlaceDetailResponseDto> findPlaceForUpdate(Long id) {

        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

//        List<PlaceImage> images = placeImageService.findImages(id);
//        List<PlaceBlog> blogs = placeBlogService.findBlogs(id);
//        Set<PlaceFilter> filters = placeFilterService.findFilters(id);
//        PlaceDetailResponseDto dto = PlaceDetailResponseDto.fromEntity(place, images, blogs, filters);

        PlaceDetailResponseDto dto = placeQueryService.findPlaceCached(place);

        return DataResponseDto.success(dto);
    }

    // 유저 인증, 권한 Spring Security 위임
    @Caching(evict = {
            @CacheEvict(value = "place", allEntries = true),
            @CacheEvict(value = "placeCounts", key = "'total'")
    })
    @Override
    public ResponseDto save(PlaceFormRequestDto dto) {

        Place place = dto.toEntity();
        Set<PlaceFilter> placeFilters = placeFilterService.getPlaceFilters(dto.getFilters(), place);
        place.addFilters(placeFilters);

        placeRepository.save(place); // 이미지 업로드 방지 사전 save

        List<PlaceNewImageDto> newImageDtos = dto.getImages();
        placeImageService.save(place, newImageDtos);

        List<String> blogUrls = dto.getBlogUrls();
        placeBlogService.save(place, blogUrls);

        return ResponseDto.success();
    }

    // 유저 인증, 권한 Spring Security 위임
    @Caching(evict = {
        @CacheEvict(value = "place", allEntries = true),
        @CacheEvict(value = "placeDetail", key = "#id"),
    })
    @Override
    public ResponseDto update(Long id, PlaceFormUpdateRequestDto dto) {

        Place place = placeRepository.findByIdWithPlaceImages(id)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));
        Set<PlaceFilter> placeFilters = placeFilterService.getPlaceFilters(dto.getFilters(), place);
        place.updateFilters(placeFilters);

        Place updatePlace = dto.textFieldToEntity();
        place.update(updatePlace);


        // 블로그 전체를 지우고 다시 등록
        List<String> blogUrls = dto.getBlogUrls();
        if (blogUrls != null && !blogUrls.isEmpty()) {
            placeBlogService.deleteAll(place);
            placeBlogService.save(place, blogUrls);
        }

        List<PlaceUpdateImagesDto> updateImages = dto.getUpdateImages();
        placeImageService.update(place, updateImages);

        List<Long> removedImageIds = dto.getRemovedImageIds();
        placeImageService.delete(place, removedImageIds);

        List<PlaceNewImageDto> newImageDtos = dto.getNewImages();
        placeImageService.save(place, newImageDtos);

        return ResponseDto.success();
    }

    // 유저 인증, 권한 Spring Security 위임
    @Caching(evict = {
        @CacheEvict(value = "place", allEntries = true),
        @CacheEvict(value = "placeCounts", key = "'total'")
    })
    @Override
    public ResponseDto delete(List<Long> ids) {

        Cache placeDetailCache = cacheManager.getCache("placeDetail");
        if (placeDetailCache != null) {
            ids.forEach(id -> placeDetailCache.evict(id));
        }

        List<Place> placesToDelete = placeRepository.findAllById(ids);
        if (placesToDelete.isEmpty()) {
            throw new CustomException(ApiResponseCode.NOT_EXIST_PLACE);
        }

        placeImageService.deleteAll(placesToDelete);
        placeRepository.deleteAll(placesToDelete);

        return ResponseDto.success();
    }

    @Override
    public DataResponseDto<List<PlaceResponseDto>> getTop3() {

        List<PlaceResponseDto> top3 = placeQueryService.getTop3Cached();
        return DataResponseDto.success(top3);
    }

    @Transactional(readOnly = true)
    @Override
    public DataResponseDto<SliceResponseDto<PlaceResponseDto>> searchFilterPlace(PlaceSearchRequestDto dto, Pageable pageable) {

//        SliceResponseDto<PlaceResponseDto> responseDtos = placeQueryService.searchFilterPlaceCached(dto, pageable);
        SliceResponseDto<PlaceResponseDto> responseDtos = placeRepository.searchPlacesWithMultiFilters(
                dto.getKeyword(), dto.getCity(), dto.getTypes(),
                dto.getPetAccessTypes(), dto.getPetSizes(), dto.getServices(), pageable);

        return DataResponseDto.success(responseDtos);
    }

    @Transactional(readOnly = true)
    @Override
    public DataResponseDto<Long> searchFilterCountPlaces(PlaceSearchRequestDto dto) {

        Long totalCount = placeRepository.getSearchPlacesTotalCount(dto.getKeyword(), dto.getCity(), dto.getTypes(),
                dto.getPetAccessTypes(), dto.getPetSizes(), dto.getServices());

        return DataResponseDto.success(totalCount);
    }

    @Override
    public DataResponseDto<List<PlaceResponseDto>> recentPlaces(int limit) {

        List<PlaceResponseDto> responseDtos = placeQueryService.recentPlacesCached(limit);

        return DataResponseDto.success(responseDtos);
    }

    @Override
    public DataResponseDto<List<PlaceResponseDto>> randomPlaces(int limit) {

        List<PlaceResponseDto> responseDtos = placeQueryService.randomPlacesCached(limit);

        return DataResponseDto.success(responseDtos);
    }

    @Override
    public DataResponseDto<Long> countPlaces() {

        long count = placeQueryService.countPlacesCached();

        return DataResponseDto.success(count);
    }

    private <T> SliceResponseDto<T> toSliceResponse(Slice<?> slice, List<T> content) {
        SliceInfo info = new SliceInfo(
                slice.getNumber(),
                slice.getSize(),
                slice.isFirst(),
                slice.isLast(),
                slice.hasNext()
        );
        return new SliceResponseDto<>(info, content);
    }
}
