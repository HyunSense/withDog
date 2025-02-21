package withdog.domain.place.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        // 기존: Image, Filter fetch join, Blog Lazy loading -> 조회 쿼리 감소, 일관성 X
        // 변경: Entity 별 조회 -> 조회 쿼리 증가, 일관성 O
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

        List<PlaceImage> images = placeImageService.findImages(id);
        List<PlaceBlog> blogs = placeBlogService.findBlogs(id);
        Set<PlaceFilter> filters = placeFilterService.findFilters(id);
        placeWeeklyStatsService.increaseHitCount(place);
        PlaceDetailResponseDto dto = PlaceDetailResponseDto.fromEntity(place, images, blogs, filters);

        return DataResponseDto.success(dto);
    }

    @Transactional(readOnly = true)
    @Override
    public DataResponseDto<PlaceDetailResponseDto> findPlaceForUpdate(Long id) {

        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

        List<PlaceImage> images = placeImageService.findImages(id);
        List<PlaceBlog> blogs = placeBlogService.findBlogs(id);
        Set<PlaceFilter> filters = placeFilterService.findFilters(id);
        PlaceDetailResponseDto dto = PlaceDetailResponseDto.fromEntity(place, images, blogs, filters);

        return DataResponseDto.success(dto);
    }

    // 유저 인증, 권한 Spring Security 위임
    @Override
    public ResponseDto save(PlaceFormRequestDto dto) {

        Place place = dto.toEntity();

        Set<PlaceFilter> placeFilters = placeFilterService.getPlaceFilters(dto.getFilters(), place);

        place.addFilters(placeFilters);

        List<PlaceNewImageDto> newImageDtos = dto.getImages();

        if (newImageDtos != null && !newImageDtos.isEmpty()) {
            placeImageService.save(place, newImageDtos);
        }

        List<String> blogUrls = dto.getBlogUrls();
        if (blogUrls != null && !blogUrls.isEmpty()) {
            placeBlogService.save(place, blogUrls);
        }

        placeRepository.save(place);
        return ResponseDto.success();
    }

    // 유저 인증, 권한 Spring Security 위임
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

        // TODO: 테이블상에는 삭제가되었는데 클라우드에서 실제 데이터는 삭제할 것인지?, 삭제한다면 어떻게 할것인지?

        List<PlaceUpdateImagesDto> updateImages = dto.getUpdateImages(); // 수정된 이미지의 id 와 position

        if (updateImages != null && !updateImages.isEmpty()) {
            placeImageService.update(place, updateImages);
        }

        List<Long> removedImageIds = dto.getRemovedImageIds();
        if (removedImageIds != null && !removedImageIds.isEmpty()) {
            placeImageService.delete(place, removedImageIds);
        }

        List<PlaceNewImageDto> newImageDtos = dto.getNewImages();
        if (newImageDtos != null && !newImageDtos.isEmpty()) {
            placeImageService.save(place, newImageDtos);
        }

        return ResponseDto.success();
    }

    // 유저 인증, 권한 Spring Security 위임
    @Override
    public ResponseDto delete(List<Long> ids) {

        // 내부적으로 select 조회
        placeRepository.deleteAllById(ids);

        return ResponseDto.success();
    }

    @Transactional(readOnly = true)
    @Override
    public DataResponseDto<List<PlaceResponseDto>> getTop3() {
        //TODO: 하드코딩 수정 필요
        int pageNumber = 0;
        int pageSize = 3;

        List<PlaceWeeklyStats> placeWeeklyStatsList = placeWeeklyStatsService.getTop3PlaceWeeklyStats(PageRequest.of(pageNumber, pageSize));
        List<PlaceResponseDto> top3 = PlaceResponseDto.fromEntityPlaceWeeklyStatsList(placeWeeklyStatsList);

        return DataResponseDto.success(top3);
    }

    @Transactional(readOnly = true)
    @Override
    public DataResponseDto<SliceResponseDto<PlaceResponseDto>> searchFilterPlace(PlaceSearchRequestDto dto, Pageable pageable) {

        Slice<Place> slicePlaces = placeRepository.searchPlacesWithMultiFilters(
                dto.getKeyword(), dto.getCity(), dto.getTypes(),
                dto.getPetAccessTypes(), dto.getPetSizes(), dto.getServices(), pageable);

        List<PlaceResponseDto> placeResponseDto = PlaceResponseDto.fromEntityList(slicePlaces.getContent());
        SliceResponseDto<PlaceResponseDto> responseDtos = toSliceResponse(slicePlaces, placeResponseDto);

        return DataResponseDto.success(responseDtos);
    }

    @Override
    public DataResponseDto<Long> searchFilterCountPlaces(PlaceSearchRequestDto dto) {

        Long totalCount = placeRepository.getSearchPlacesTotalCount(dto.getKeyword(), dto.getCity(), dto.getTypes(),
                dto.getPetAccessTypes(), dto.getPetSizes(), dto.getServices());

        return DataResponseDto.success(totalCount);
    }

    @Transactional(readOnly = true)
    @Override
    public DataResponseDto<List<PlaceResponseDto>> recentPlaces(int limit) {

        PageRequest pageRequest = PageRequest.of(0, limit);

        List<Place> places = placeRepository.findByOrderByCreatedAtDesc(pageRequest);
        List<PlaceResponseDto> responseDtos = PlaceResponseDto.fromEntityList(places);

        return DataResponseDto.success(responseDtos);
    }

    @Transactional(readOnly = true)
    @Override
    public DataResponseDto<List<PlaceResponseDto>> randomPlaces(int limit) {

        PageRequest pageRequest = PageRequest.of(0, limit);
        List<Place> places = placeRepository.findByOrderByRandom(pageRequest);
        List<PlaceResponseDto> responseDtos = PlaceResponseDto.fromEntityList(places);

        return DataResponseDto.success(responseDtos);
    }

    @Override
    public DataResponseDto<Long> countPlaces() {

        Long count = placeRepository.countBy();

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
