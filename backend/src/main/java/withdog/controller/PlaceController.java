package withdog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import withdog.config.auth.CustomUserDetails;
import withdog.dto.request.PlaceRegisterRequestDto;
import withdog.dto.request.TempPlaceRegisterRequestDto;
import withdog.dto.response.DataResponseDto;
import withdog.dto.response.PlaceDetailResponseDto;
import withdog.dto.response.PlaceResponseDto;
import withdog.dto.response.ResponseDto;
import withdog.service.ImageUploadService;
import withdog.service.PlaceService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PlaceController {

    private final PlaceService placeService;
    private final ImageUploadService imageUploadService;

    //TODO: 변경필요, Image Resizing 필요
    @PostMapping("/places")
//    public ResponseEntity<ResponseDto> savePlace(@Valid @RequestBody PlaceRegisterRequestDto dto) {
    public ResponseEntity<ResponseDto> savePlace(@Valid @RequestPart("placeData") TempPlaceRegisterRequestDto dto, @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        log.info("PlaceRegisterRequestDto: {}", dto);
        log.info("images.size(): {}", images.size());

        ResponseDto responseBody = placeService.tempLocalSave(dto, images);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success());
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

    @GetMapping("/places/{id}/bookmarks/status")
    public ResponseEntity<ResponseDto> getBookmarkStatus(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id) {

        ResponseDto responseBody = placeService.isBookmarked(customUserDetails.getId(), id);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PostMapping("/places/{id}/bookmarks")
    public ResponseEntity<ResponseDto> addBookmark(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id) {

        ResponseDto responseBody = placeService.addBookmark(customUserDetails.getId(), id);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping("/places/{id}/bookmarks")
    public ResponseEntity<ResponseDto> deleteBookmark(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id) {

        ResponseDto responseBody = placeService.deleteBookmark(customUserDetails.getId(), id);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/top3")
    public ResponseEntity<ResponseDto> getTop3Places(@RequestParam(required = false, name = "category") String category) {

        ResponseDto responseBody = placeService.getTop3(category);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
