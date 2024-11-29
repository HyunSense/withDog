package withdog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import withdog.common.ApiResponseCode;
import withdog.dto.BlogExtractDto;
import withdog.dto.request.PlaceRegisterRequestDto;
import withdog.dto.request.TempPlaceRegisterRequestDto;
import withdog.dto.response.*;
import withdog.entity.*;
import withdog.exception.CustomException;
import withdog.repository.*;
import withdog.util.BlogMetaDataExtractor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PlaceServiceImpl implements PlaceService {

    private final CategoryRepository categoryRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;
    private final PlaceWeeklyStatsRepository placeWeeklyStatsRepository;
    private final DynamicQueryPlaceRepository dynamicQueryPlaceRepository;
    private final ImageUploadService imageUploadService;

    @Override
    public DataResponseDto<Slice<PlaceResponseDto>> findAllPlace(String category, Pageable pageable) {

        Slice<Place> places;

        if (category != null) {
            Category findCategory = categoryRepository.findByName(category)
                    .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_CATEGORY));
            places = placeRepository.findAllPlacesByCategoryId(findCategory.getId(), pageable);

        } else {
            places = placeRepository.findAllPlaces(pageable);
        }

        //TODO: SliceResponse, SortResponse 따로 만들지 고민
        Slice<PlaceResponseDto> dto = places.map(p -> PlaceResponseDto.builder()
                .place(p).build());

        return DataResponseDto.success(dto);
    }

    @Override
    public DataResponseDto<PlaceDetailResponseDto> findPlace(Long id) {

        // OneToMany 2개의 컬렉션
        // PlaceImages fetch join 후 PlaceBlogs 지연 로딩 조회
        Place place = placeRepository.findByIdWithCategoryAndPlaceImages(id)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

        // 추가로직 start---
        LocalDate weekMonday = LocalDate.now().with(DayOfWeek.MONDAY);
        PlaceWeeklyStats placeWeeklyStats = placeWeeklyStatsRepository.findByPlaceAndWeekStartDate(place, weekMonday)
                .orElseGet(() ->
                        placeWeeklyStatsRepository.save(
                                PlaceWeeklyStats.builder().place(place).weekStartDate(weekMonday).build()
                        )
                );
        placeWeeklyStats.increaseHit();
        // 추가로직 end---
//        place.increaseHit();
        PlaceDetailResponseDto dto = PlaceDetailResponseDto.builder().place(place).build();

        return DataResponseDto.success(dto);
    }

    @Override
    public ResponseDto save(PlaceRegisterRequestDto dto) {

        Category category = categoryRepository.findByName(dto.getCategoryName())
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_CATEGORY));

        //TODO: 애초에 이미지를 가져오지않음 이미지 url만 필요로함
        //TODO: 이미지를 가져오는 로직 필요 (aws s3, cloudFront?)
        //TODO: placeImageRepository에 저장 필요
        //TODO: Image 처리 필수

        //TODO: Image Name이 필요할꺼같다.
        int position = 1;
        Place place = dto.toEntity(category);
        List<String> imageUrls = dto.getImageUrls();
        for (String imageUrl : imageUrls) {
            PlaceImage placeImage = PlaceImage.builder()
                    .place(place)
                    .imageUrl(imageUrl)
                    .imagePosition(position++)
                    .build();
            place.addImage(placeImage);
        }

        List<String> blogUrls = dto.getBlogUrls();
        try {
            for (String blogUrl : blogUrls) {
                BlogExtractDto extractDto = BlogMetaDataExtractor.extract(blogUrl);
                PlaceBlog placeBlog = extractDto.toEntity(place, blogUrl);
                place.addBlog(placeBlog);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new CustomException(ApiResponseCode.SERVER_ERROR);
        }
//        catch (IllegalArgumentException e) { // Jsoup IOException, IllegalArgumentException 일괄 처리
//            log.info(e.getMessage());
//            throw new CustomException(ApiResponseCode.NOT_BLANK_CONTENTS);
//        } catch (IOException e) {
//            log.info(e.getMessage());
//            throw new CustomException(ApiResponseCode.SERVER_ERROR);
//        }

        placeRepository.save(place);

        return ResponseDto.success();
    }

    @Override
    public ResponseDto tempLocalSave(TempPlaceRegisterRequestDto dto, List<MultipartFile> images) {

        Category category = categoryRepository.findByName(dto.getCategory())
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_CATEGORY));

        int position = 1;
        Place place = dto.toEntity(category);

        // 임시 로컬 이미지 저장 서비스
        List<String> imageUrls = imageUploadService.saveLocal(images);

        if (!imageUrls.isEmpty()) {
            for (String imageUrl : imageUrls) {
                PlaceImage placeImage = PlaceImage.builder()
                        .place(place)
                        .imageUrl(imageUrl)
                        .imagePosition(position++)
                        .build();
                place.addImage(placeImage);
            }
        }

        List<String> blogUrls = dto.getBlogUrls();

        try {
            for (String blogUrl : blogUrls) {
                BlogExtractDto extractDto = BlogMetaDataExtractor.extract(blogUrl);
                PlaceBlog placeBlog = extractDto.toEntity(place, blogUrl);
                place.addBlog(placeBlog);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new CustomException(ApiResponseCode.SERVER_ERROR);
        }

        placeRepository.save(place);

        return ResponseDto.success();
    }

    //TODO: PUT or PATCH ?
    @Override
    public void update(Long id, PlaceRegisterRequestDto dto) {

        Category category = categoryRepository.findByName(dto.getCategoryName())
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_CATEGORY));

        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

    }

    @Override
    public ResponseDto delete(Long memberId, Long id) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_MEMBER));

        //TODO: role enum 리펙토링 필요
        String role = member.getRole();

        if (!role.equals("ROLE_ADMIN")) {
            throw new CustomException(ApiResponseCode.PERMISSION_DENIED);
        };

        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

        placeRepository.delete(place);

        return ResponseDto.success();
    }

    //TODO: toList(), Arrays.asList, Collectors.toList() 어떤것을 사용할지
    @Override
    public ResponseDto getTop3(String category) {

        List<PlaceWeeklyStats> PlaceWeeklyStatsList;
        int pageNumber = 0;
        int pageSize = 3;

        if (category != null) {
            Category findCategory = categoryRepository.findByName(category)
                    .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_CATEGORY));
            PlaceWeeklyStatsList = placeWeeklyStatsRepository.findTop3ByCategory(findCategory, PageRequest.of(pageNumber, pageSize));
        } else {

            PlaceWeeklyStatsList = placeWeeklyStatsRepository.findTop3(PageRequest.of(pageNumber, pageSize));
        }


        List<PlaceResponseDto> top3 = PlaceWeeklyStatsList.stream().map(p -> PlaceResponseDto.builder().place(p.getPlace()).build()).collect(Collectors.toList());

        return DataResponseDto.success(top3);
    }

    /*@Override
    public ResponseDto toggleBookmark(Long memberId, Long placeId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_MEMBER));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

        //TODO: findBy로 바꿔야될지 확인
        boolean isExists = bookmarkRepository.existsByMemberAndPlace(member, place);

        if (isExists) {
            bookmarkRepository.deleteByMemberAndPlace(member, place);
            place.decreaseBookmarkCount();
        } else {
            Bookmark bookmark = Bookmark.builder()
                    .member(member)
                    .place(place)
                    .build();
            bookmarkRepository.save(bookmark);
            place.increaseBookmarkCount();
        }

        return ResponseDto.success();
    }*/

    @Override
    public ResponseDto isBookmarked(Long memberId, Long placeId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_MEMBER));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));
        boolean isBookmarked = bookmarkRepository.existsByMemberAndPlace(member, place);
        BookmarkStatusDto dto = BookmarkStatusDto.builder().bookmarked(isBookmarked).build();

        return DataResponseDto.success(dto);
    }

    @Override
    public ResponseDto addBookmark(Long memberId, Long placeId) {
        //TODO 굳이 쿼리조회를 2번씩 할필요가있을까
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_MEMBER));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));
        Bookmark bookmark = Bookmark.builder().member(member).place(place).build();

        //TODO: save 하기전에 exists(존재 여부)를 확인해야되는지? 확인을 해야된다면 로직자체가 잘못된것이다.
        //TODO: 단순하게 생각하면, 당연히 데이터 없으니까 save를 하는것이다?
        bookmarkRepository.save(bookmark);

        // 추가로직 start---
        LocalDate weekMonday = LocalDate.now().with(DayOfWeek.MONDAY);
        PlaceWeeklyStats placeWeeklyStats = placeWeeklyStatsRepository.findByPlaceAndWeekStartDate(place, weekMonday)
                .orElseGet(() ->
                        placeWeeklyStatsRepository.save(
                                PlaceWeeklyStats.builder().place(place).weekStartDate(weekMonday).build()
                        )
                );
        placeWeeklyStats.increaseBookmarkCount();
        // 추가로직 end---
//        place.increaseBookmarkCount();

        return ResponseDto.success();
    }

    @Override
    public ResponseDto deleteBookmark(Long memberId, Long placeId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_MEMBER));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

        bookmarkRepository.deleteByMemberAndPlace(member, place);

        // 추가로직 start---
        LocalDate weekMonday = LocalDate.now().with(DayOfWeek.MONDAY);
        PlaceWeeklyStats placeWeeklyStats = placeWeeklyStatsRepository.findByPlaceAndWeekStartDate(place, weekMonday)
                .orElseGet(() ->
                        placeWeeklyStatsRepository.save(
                                PlaceWeeklyStats.builder().place(place).weekStartDate(weekMonday).build()
                        )
                );
        placeWeeklyStats.decreaseBookmarkCount();
        // 추가로직 end---
//        place.decreaseBookmarkCount();

        return ResponseDto.success();
    }
}
