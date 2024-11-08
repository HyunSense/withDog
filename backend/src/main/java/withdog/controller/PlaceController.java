package withdog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import withdog.dto.request.PlaceRegisterRequestDto;
import withdog.dto.response.DataResponseDto;
import withdog.dto.response.PlaceDetailResponseDto;
import withdog.dto.response.PlaceResponseDto;
import withdog.dto.response.ResponseDto;
import withdog.service.PlaceService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/places")
    public ResponseEntity<ResponseDto> savePlace(@Valid @RequestBody PlaceRegisterRequestDto dto) {

        ResponseDto responseBody = placeService.save(dto);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places")
    public ResponseEntity<DataResponseDto<Slice<PlaceResponseDto>>> getAllPlaces(@RequestParam(required = false ,name = "category") String category, @PageableDefault(size = 10) Pageable pageable) {

        DataResponseDto<Slice<PlaceResponseDto>> responseBody = placeService.findAllPlace(category, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/{id}")
    public ResponseEntity<DataResponseDto<PlaceDetailResponseDto>> getPlace(@PathVariable Long id) {

        DataResponseDto<PlaceDetailResponseDto> responseBody = placeService.findPlace(id);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
