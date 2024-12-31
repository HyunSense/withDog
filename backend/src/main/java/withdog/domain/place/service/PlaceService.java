package withdog.domain.place.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.domain.bookmark.dto.request.DeleteBookmarksRequestDto;
import withdog.domain.place.dto.request.PlaceDeleteRequestDto;
import withdog.domain.place.dto.request.PlaceFormRequestDto;
import withdog.domain.place.dto.request.PlaceFormUpdateRequestDto;
import withdog.domain.place.dto.response.BookmarkedPlaceResponseDto;
import withdog.domain.place.dto.response.PlaceDetailResponseDto;
import withdog.domain.place.dto.response.PlaceResponseDto;

import java.io.IOException;
import java.util.List;

public interface PlaceService {

    DataResponseDto<Slice<PlaceResponseDto>> findAllPlace(String category, Pageable pageable);

    DataResponseDto<PlaceDetailResponseDto> findPlace(Long id);

    ResponseDto save(PlaceFormRequestDto dto);

//    ResponseDto tempLocalSave(PlaceFormRequestDto dto);

    ResponseDto update(Long id, PlaceFormUpdateRequestDto dto);

    ResponseDto delete(Long memberId, PlaceDeleteRequestDto dto);

    ResponseDto getTop3(String category);
}
