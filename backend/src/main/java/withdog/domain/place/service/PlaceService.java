package withdog.domain.place.service;

import org.springframework.data.domain.Pageable;
import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.common.dto.response.SliceResponseDto;
import withdog.domain.place.dto.request.PlaceFormRequestDto;
import withdog.domain.place.dto.request.PlaceFormUpdateRequestDto;
import withdog.domain.place.dto.request.PlaceSearchRequestDto;
import withdog.domain.place.dto.response.PlaceDetailResponseDto;
import withdog.domain.place.dto.response.PlaceResponseDto;

import java.util.List;

public interface PlaceService {

    DataResponseDto<SliceResponseDto<PlaceResponseDto>> findAllPlace(Pageable pageable);

    DataResponseDto<PlaceDetailResponseDto> findPlace(Long id);

    DataResponseDto<PlaceDetailResponseDto> findPlaceForUpdate(Long id);

    ResponseDto save(PlaceFormRequestDto dto);

    ResponseDto update(Long id, PlaceFormUpdateRequestDto dto);

    ResponseDto delete(List<Long> ids);

    DataResponseDto<List<PlaceResponseDto>> getTop3();

    DataResponseDto<SliceResponseDto<PlaceResponseDto>> searchFilterPlace(PlaceSearchRequestDto dto, Pageable pageable);

    DataResponseDto<Long> searchFilterCountPlaces(PlaceSearchRequestDto dto);

    DataResponseDto<List<PlaceResponseDto>> recentPlaces(int limit);

    DataResponseDto<List<PlaceResponseDto>> randomPlaces(int limit);

    DataResponseDto<Long> countPlaces();
}
