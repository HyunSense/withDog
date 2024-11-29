package withdog.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;
import withdog.dto.request.PlaceRegisterRequestDto;
import withdog.dto.request.TempPlaceRegisterRequestDto;
import withdog.dto.response.DataResponseDto;
import withdog.dto.response.PlaceDetailResponseDto;
import withdog.dto.response.PlaceResponseDto;
import withdog.dto.response.ResponseDto;
import withdog.entity.Category;

import java.util.List;

public interface PlaceService {

    DataResponseDto<Slice<PlaceResponseDto>> findAllPlace(String category, Pageable pageable);

    DataResponseDto<PlaceDetailResponseDto> findPlace(Long id);

    ResponseDto save(PlaceRegisterRequestDto dto);

    ResponseDto tempLocalSave(TempPlaceRegisterRequestDto dto, List<MultipartFile> images);

    void update(Long id, PlaceRegisterRequestDto dto);

    ResponseDto delete(Long memberId, Long id);

    ResponseDto getTop3(String category);

//    ResponseDto toggleBookmark(Long placeId, Long memberId);

    ResponseDto isBookmarked(Long memberId, Long placeId);

    ResponseDto addBookmark(Long memberId, Long placeId);

    ResponseDto deleteBookmark(Long memberId, Long placeId);
}
