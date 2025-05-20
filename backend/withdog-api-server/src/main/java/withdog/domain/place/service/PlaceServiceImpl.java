package withdog.domain.place.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
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
import withdog.messaging.model.place.PlaceActionMessage;
import withdog.messaging.model.place.PlaceFilterMessage;
import withdog.messaging.producer.UserEventProducer;

import java.util.*;

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
    private final UserEventProducer userEventPublisher;

    private final RedisTemplate<String, String> redisTemplate;

    //    private final KafkaTemplate<String, String> kafkaTemplate;
    @Transactional(readOnly = true)
    @Override
    public DataResponseDto<SliceResponseDto<PlaceResponseDto>> findAllPlace(Pageable pageable) {

        Slice<Place> slicePlaces = placeRepository.findAllPlaces(pageable);

        List<PlaceResponseDto> placeResponseDto = PlaceResponseDto.fromEntityList(slicePlaces.getContent());
        SliceResponseDto<PlaceResponseDto> responseDtos = toSliceResponse(slicePlaces, placeResponseDto);

        return DataResponseDto.success(responseDtos);
    }

    @Override
    public DataResponseDto<PlaceDetailResponseDto> findPlace(Long id, String sessionId, Long memberId) {

        // 기존: Image, Filter fetch join, Blog Lazy loading -> 조회 쿼리 감소, 일관성 X
        // 변경: Entity 별 조회 -> 조회 쿼리 증가, 일관성 O
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

        List<PlaceImage> images = placeImageService.findImages(id);
        List<PlaceBlog> blogs = placeBlogService.findBlogs(id);
        Set<PlaceFilter> filters = placeFilterService.findFilters(id);
        placeWeeklyStatsService.increaseHitCount(place);
        PlaceDetailResponseDto dto = PlaceDetailResponseDto.fromEntity(place, images, blogs, filters);

        PlaceActionMessage userEvent = PlaceActionMessage.builder()
                .eventType("views")
                .placeId(id)
                .sessionId(sessionId)
                .memberId(memberId)
                .build();

        userEventPublisher.send("place-views", sessionId, userEvent);


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

        placeRepository.save(place); // 이미지 업로드 방지 사전 save

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


    //TODO: 추후 요청 DTO 유연하게 변경 (검색관련 객체 분리 및 sessionID, memberID DTO 통합)
    @Transactional(readOnly = true)
    @Override
    public DataResponseDto<SliceResponseDto<PlaceResponseDto>> searchFilterPlace(PlaceSearchRequestDto dto, Pageable pageable, String sessionId, Long memberId) {

        SliceResponseDto<PlaceResponseDto> slicePlaces = placeRepository.searchPlacesWithMultiFilters(
                dto.getKeyword(), dto.getCity(), dto.getTypes(),
                dto.getPetAccessTypes(), dto.getPetSizes(), dto.getServices(), pageable);

//        Map<String, List<String>> filters =
//                Map.of("types", dto.getTypes(), "city", dto.getCity(), "petAccessTypes", dto.getPetAccessTypes(), "petSizes", dto.getPetSizes(), "services", dto.getServices());

        Map<String, List<String>> filters = new HashMap<>();
        filters.put("types", dto.getTypes());
        filters.put("city", dto.getCity());
        filters.put("petAccessTypes", dto.getPetAccessTypes());
        filters.put("petSizes", dto.getPetSizes());
        filters.put("services", dto.getServices());

        PlaceFilterMessage userEvent = PlaceFilterMessage.builder()
                .sessionId(sessionId)
                .memberId(memberId)
                .keyword(dto.getKeyword())
                .filters(filters)
                .build();

        userEventPublisher.send("place-filters", sessionId, userEvent);

        //TODO: 캐싱 필요
        // 인기순 정렬
        if (dto.getSort() != null && dto.getSort().equals("popular")) {
            Map<Long, Double> popularityScores = new HashMap<>();
            List<PlaceResponseDto> placeResponseDtos = slicePlaces.getContent();

            for (PlaceResponseDto place : placeResponseDtos) {
                Double score = redisTemplate.opsForZSet().score("popular_places", place.getId().toString());
                popularityScores.put(place.getId(), score != null ? score : 0.0);
            }

            placeResponseDtos.sort((p1, p2) -> {
                Double score1 = popularityScores.getOrDefault(p1.getId(), 0.0);
                Double score2 = popularityScores.getOrDefault(p2.getId(), 0.0);

                return Double.compare(score2, score1); // 내림차순
            });

            slicePlaces = new SliceResponseDto<>(slicePlaces.getSliceInfo(), placeResponseDtos);
        }

        return DataResponseDto.success(slicePlaces);
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
