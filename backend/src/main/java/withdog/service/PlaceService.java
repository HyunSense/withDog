package withdog.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import withdog.dto.request.PlaceRegisterRequestDto;
import withdog.dto.response.DataResponseDto;
import withdog.dto.response.PlaceDetailResponseDto;
import withdog.dto.response.PlaceResponseDto;
import withdog.dto.response.ResponseDto;

public interface PlaceService {

    DataResponseDto<Slice<PlaceResponseDto>> findAllPlace(String category, Pageable pageable);

    DataResponseDto<PlaceDetailResponseDto> findPlace(Long id);

    ResponseDto save(PlaceRegisterRequestDto dto);

    void update(Long id, PlaceRegisterRequestDto dto);

    void delete(Long id);

}
