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
import withdog.domain.place.dto.request.*;
import withdog.domain.place.dto.response.PlaceDetailResponseDto;
import withdog.domain.place.dto.response.PlaceResponseDto;
import withdog.domain.place.entity.Category;
import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.filter.FilterOption;
import withdog.domain.place.entity.filter.PlaceFilter;
import withdog.domain.place.repository.CategoryRepository;
import withdog.domain.place.repository.PlaceRepository;
import withdog.domain.place.repository.filter.FilterOptionRepository;
import withdog.domain.stats.entity.PlaceWeeklyStats;
import withdog.domain.stats.service.PlaceWeeklyStatsService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PlaceServiceImpl implements PlaceService {

    private final CategoryRepository categoryRepository;
    private final PlaceRepository placeRepository;

    private final PlaceWeeklyStatsService placeWeeklyStatsService;
    private final PlaceImageService placeImageService;
    private final PlaceBlogService placeBlogService;
    private final FilterOptionRepository filterOptionRepository;

    @Transactional(readOnly = true)
    @Override
    public DataResponseDto<Slice<PlaceResponseDto>> findAllPlace(int categoryId, Pageable pageable) {

        if (categoryId != 0 && !categoryRepository.existsById(categoryId)) {
            throw new CustomException(ApiResponseCode.NOT_EXIST_CATEGORY);
        }

        Slice<Place> places;

        if (categoryId == 0) {
            places = placeRepository.findAllPlaces(pageable);
        } else {
            places = placeRepository.findAllPlacesByCategoryId(categoryId, pageable);
        }

        Slice<PlaceResponseDto> dto = PlaceResponseDto.fromEntitySlice(places);

        return DataResponseDto.success(dto);
    }

    @Override
    public DataResponseDto<PlaceDetailResponseDto> findPlace(Long id) {

        // OneToMany 2개의 컬렉션
        //TODO: PlaceImages fetch join, PlaceBlogs 별도 쿼리 조회 또는
        //TODO: PlaceImages 지연로딩을 할지 고민 필요
        Place place = placeRepository.findByIdWithCategoryAndPlaceImages(id)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

        placeWeeklyStatsService.increaseHitCount(place);
        PlaceDetailResponseDto dto = PlaceDetailResponseDto.fromEntity(place);
        return DataResponseDto.success(dto);
    }

    // 유저 인증, 권한 Spring Security 위임
//    @Override
//    public ResponseDto save(PlaceFormRequestDto dto) {
//
//        Category category = categoryRepository.findById(dto.getCategoryId())
//                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_CATEGORY));
//        Place place = dto.toEntity(category);
//        placeRepository.save(place);
//
//        List<PlaceNewImageDto> newImageDtos = dto.getImages();
//
//        if (newImageDtos != null && !newImageDtos.isEmpty()) {
//            placeImageService.save(place, newImageDtos);
//        }
//
//        List<String> blogUrls = dto.getBlogUrls();
//        if (blogUrls != null && !blogUrls.isEmpty()) {
//            placeBlogService.save(place, blogUrls);
//        }
//
//        return ResponseDto.success();
//    }

    @Override
    public ResponseDto save(PlaceFormRequestDto2 dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_CATEGORY));
        Place place = dto.toEntity(category);
        placeRepository.save(place);

        List<Integer> filterOptionIds = dto.getFilterOptionId();
        for (Integer filterOptionId : filterOptionIds) {
            FilterOption filterOption = filterOptionRepository.findById(filterOptionId)
                    .orElseThrow(() -> new IllegalArgumentException("예외처리 필요함"));

            PlaceFilter placeFilter = new PlaceFilter(place, filterOption);
            place.addFilter(placeFilter);
        }


        List<PlaceNewImageDto> newImageDtos = dto.getImages();

        if (newImageDtos != null && !newImageDtos.isEmpty()) {
            placeImageService.save(place, newImageDtos);
        }

        List<String> blogUrls = dto.getBlogUrls();
        if (blogUrls != null && !blogUrls.isEmpty()) {
            placeBlogService.save(place, blogUrls);
        }

        return ResponseDto.success();
    }

    // 유저 인증, 권한 Spring Security 위임
    @Override
    public ResponseDto update(Long id, PlaceFormUpdateRequestDto dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_CATEGORY));

        Place place = placeRepository.findByIdWithCategoryAndPlaceImages(id)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

        Place updatePlace = dto.textFieldToEntity(category);
        place.update(updatePlace);


        // 블로그 전체를 지우고 다시 등록
        List<String> blogUrls = dto.getBlogUrls();
        if (blogUrls != null && !blogUrls.isEmpty()) {
            placeBlogService.deleteAll(place);
            placeBlogService.save(place, blogUrls);
        }

        // TODO: 테이블상에는 삭제가되었는데 로컬에서 실제 데이터는 삭제할 것인지?
        // TODO: 삭제한다면 어떻게 할것인지?

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
    public ResponseDto delete(Long memberId, PlaceDeleteRequestDto dto) {

        //TODO: 벌크로 삭제하는것이 좋을지, 반복문으로 하나씩 삭제하는것이 좋을지
        //TODO: deleteAll, deleteAllbyId 모두 내부적으로 select 조회를한다. 추가적으로 검증이 필요하다면 findById작성 아니라면 작성X
        placeRepository.deleteAllById(dto.getIds());

        return ResponseDto.success();
    }

    //TODO: toList(), Arrays.asList, Collectors.toList() 어떤것을 사용할지
    @Transactional(readOnly = true)
    @Override
    public ResponseDto getTop3(int categoryId) {

        int pageNumber = 0;
        int pageSize = 3;

        List<PlaceWeeklyStats> placeWeeklyStatsList = placeWeeklyStatsService.getTop3PlaceWeeklyStats(categoryId, PageRequest.of(pageNumber, pageSize));
        List<PlaceResponseDto> top3 = PlaceResponseDto.fromEntityPlaceWeeklyStatsList(placeWeeklyStatsList);

        return DataResponseDto.success(top3);
    }

    @Override
    public DataResponseDto<Slice<PlaceResponseDto>> searchPlace(String type, String keyword, Pageable pageable) {

        Page<Place> places = placeRepository.findAllPlacesByTypeAndKeyword(type, keyword, pageable);
        Page<PlaceResponseDto> dto = PlaceResponseDto.fromEntityPage(places);

        return DataResponseDto.success(dto);
    }

    // TODO: 데이터 반환타입 ResponseDto, DataResponseDto 통일이 필요
    @Override
    public ResponseDto searchFilterPlace(PlaceSearchRequestDto dto, Pageable pageable) {

        Slice<Place> slicePlaces = placeRepository.searchPlacesWithMultiFilters(
                dto.getKeyword(), dto.getCity(), dto.getTypes(),
                dto.getPetAccessTypes(), dto.getPetSizes(), dto.getServices(), pageable);

        List<Place> places = slicePlaces.getContent();
        boolean hasNext = slicePlaces.hasNext();
        boolean first = slicePlaces.isFirst();
        boolean last = slicePlaces.isLast();
        int page = slicePlaces.getNumber();
        int size = slicePlaces.getSize();

        List<PlaceResponseDto> placeResponseDto = PlaceResponseDto.fromEntityList(places);
        SliceInfo sliceInfo = new SliceInfo(page, size, first, last, hasNext);
        SliceResponseDto<PlaceResponseDto> responseDtos = new SliceResponseDto<>(sliceInfo, placeResponseDto);

        return DataResponseDto.success(responseDtos);
    }

    @Override
    public ResponseDto recentPlaces(int limit) {

        PageRequest pageRequest = PageRequest.of(0, limit);

        List<Place> places = placeRepository.findByOrderByCreatedAtDesc(pageRequest);
        List<PlaceResponseDto> responseDtos = PlaceResponseDto.fromEntityList(places);

        return DataResponseDto.success(responseDtos);
    }

    @Override
    public ResponseDto randomPlaces(int limit) {

        PageRequest pageRequest = PageRequest.of(0, limit);
        List<Place> places = placeRepository.findByOrderByRandom(pageRequest);
        List<PlaceResponseDto> responseDtos = PlaceResponseDto.fromEntityList(places);

        return DataResponseDto.success(responseDtos);
    }
}
