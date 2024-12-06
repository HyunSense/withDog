package withdog.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;
import withdog.dto.request.*;
import withdog.dto.response.*;
import withdog.entity.Category;

import java.util.List;

public interface PlaceService {

    DataResponseDto<Slice<PlaceResponseDto>> findAllPlace(String category, Pageable pageable);

    DataResponseDto<PlaceDetailResponseDto> findPlace(Long id);

    ResponseDto save(PlaceRegisterRequestDto dto);

    ResponseDto tempLocalSave(TempPlaceRegisterRequestDto dto, List<MultipartFile> images);

    ResponseDto tempLocalSave2(PlaceFormRequestDto dto);

    ResponseDto update(Long id, TempPlaceUpdateRequestDto dto, List<MultipartFile> updateImages);

    ResponseDto delete(Long memberId, PlaceDeleteRequestDto dto);

    ResponseDto getTop3(String category);

//    ResponseDto toggleBookmark(Long placeId, Long memberId);
    DataResponseDto<List<BookmarkedPlaceResponseDto>> findAllBookmarkedPlace(Long memberId);

    ResponseDto isBookmarked(Long memberId, Long placeId);

    ResponseDto addBookmark(Long memberId, Long placeId);

    ResponseDto deleteBookmark(Long memberId, Long placeId);

    ResponseDto deleteAllByIdBookmarks(Long memberId, SelectedBookmarkRequestDto dto);
}
