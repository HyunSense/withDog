package withdog.domain.place.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.domain.place.dto.request.*;
import withdog.domain.place.dto.response.PlaceDetailResponseDto;
import withdog.domain.place.dto.response.PlaceResponseDto;

public interface PlaceService {

    DataResponseDto<Slice<PlaceResponseDto>> findAllPlace(int categoryId, Pageable pageable);

    DataResponseDto<PlaceDetailResponseDto> findPlace(Long id);

//    ResponseDto save(PlaceFormRequestDto dto);
    ResponseDto save(PlaceFormRequestDto2 dto);

    ResponseDto update(Long id, PlaceFormUpdateRequestDto dto);

    ResponseDto delete(Long memberId, PlaceDeleteRequestDto dto);

    ResponseDto getTop3(int categoryId);

    DataResponseDto<Slice<PlaceResponseDto>> searchPlace(String type, String keyword, Pageable pageable);

    ResponseDto searchFilterPlace(PlaceSearchRequestDto dto, Pageable pageable);

    ResponseDto recentPlaces(int limit);

    ResponseDto randomPlaces(int limit);
}
