package withdog.domain.place.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.common.constant.ApiResponseCode;
import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.common.exception.CustomException;
import withdog.domain.place.dto.PlaceNewImageDto;
import withdog.domain.place.dto.PlaceUpdateImagesDto;
import withdog.domain.place.dto.request.PlaceDeleteRequestDto;
import withdog.domain.place.dto.request.PlaceFormRequestDto;
import withdog.domain.place.dto.request.PlaceFormUpdateRequestDto;
import withdog.domain.place.dto.response.PlaceDetailResponseDto;
import withdog.domain.place.dto.response.PlaceResponseDto;
import withdog.domain.place.entity.Category;
import withdog.domain.place.entity.Place;
import withdog.domain.place.repository.CategoryRepository;
import withdog.domain.place.repository.PlaceRepository;
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

    @Transactional(readOnly = true)
    @Override
    public DataResponseDto<Slice<PlaceResponseDto>> findAllPlace(int categoryId, Pageable pageable) {

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
        // PlaceImages fetch join 후 PlaceBlogs 지연 로딩 조회
        Place place = placeRepository.findByIdWithCategoryAndPlaceImages(id)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

        placeWeeklyStatsService.increaseHitCount(place);
        PlaceDetailResponseDto dto = PlaceDetailResponseDto.fromEntity(place);
        return DataResponseDto.success(dto);
    }

    // 유저 인증, 권한 Spring Security 위임
    @Override
    public ResponseDto save(PlaceFormRequestDto dto) {

        //TODO: findByName -> findById 변경 필요
        Category category = categoryRepository.findByName(dto.getCategory())
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_CATEGORY));
        Place place = dto.toEntity(category);
        placeRepository.save(place);

        //TODO: PlaceNewImageDto 변경필요 name 필드추가
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

        //TODO: findByName -> findById 변경 필요
        Category category = categoryRepository.findByName(dto.getCategory())
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
        List<PlaceResponseDto> top3 = PlaceResponseDto.fromEntityList(placeWeeklyStatsList);

        return DataResponseDto.success(top3);
    }
}
