package withdog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withdog.common.ApiResponseCode;
import withdog.dto.BlogExtractDto;
import withdog.dto.request.PlaceRegisterRequestDto;
import withdog.dto.response.DataResponseDto;
import withdog.dto.response.PlaceDetailResponseDto;
import withdog.dto.response.PlaceResponseDto;
import withdog.dto.response.ResponseDto;
import withdog.entity.Category;
import withdog.entity.Place;
import withdog.entity.PlaceBlog;
import withdog.entity.PlaceImage;
import withdog.exception.CustomException;
import withdog.repository.CategoryRepository;
import withdog.repository.PlaceRepository;
import withdog.util.BlogMetaDataExtractor;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PlaceServiceImpl implements PlaceService {

    private final CategoryRepository categoryRepository;
    private final PlaceRepository placeRepository;

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

    //TODO: PUT or PATCH ?
    @Override
    public void update(Long id, PlaceRegisterRequestDto dto) {

        Category category = categoryRepository.findByName(dto.getCategoryName())
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_CATEGORY));

        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_PLACE));

    }

    @Override
    public void delete(Long id) {

    }
}
