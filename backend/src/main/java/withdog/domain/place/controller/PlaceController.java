package withdog.domain.place.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
//import withdog.aws.AwsFileService;
import withdog.aws.AwsFileService;
import withdog.common.config.auth.CustomUserDetails;
import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.domain.bookmark.dto.request.DeleteBookmarksRequestDto;
import withdog.domain.bookmark.service.BookmarkService;
import withdog.domain.place.dto.request.PlaceDeleteRequestDto;
import withdog.domain.place.dto.request.PlaceFormRequestDto;
import withdog.domain.place.dto.request.PlaceFormUpdateRequestDto;
import withdog.domain.place.dto.response.BookmarkedPlaceResponseDto;
import withdog.domain.place.dto.response.PlaceDetailResponseDto;
import withdog.domain.place.dto.response.PlaceResponseDto;
import withdog.domain.place.service.ImageUploadService;
import withdog.domain.place.service.PlaceService;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PlaceController {

    private final PlaceService placeService;
    private final BookmarkService bookmarkService;

    //TODO: 변경필요, Image Resizing 필요
    @PostMapping("/places")
    public ResponseEntity<ResponseDto> savePlace(@Valid @ModelAttribute PlaceFormRequestDto dto) {

        log.info("PlaceRegisterRequestDto: {}", dto);

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

    @PutMapping("/places/{id}")
    public ResponseEntity<ResponseDto> updatePlace(@PathVariable Long id, @Valid @ModelAttribute PlaceFormUpdateRequestDto dto) {

        log.info("PlaceUpdateRequestDto: {}", dto);
        ResponseDto responseBody = placeService.update(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping("/places")
    public ResponseEntity<ResponseDto> deletePlace(@RequestBody PlaceDeleteRequestDto dto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        log.info("PlaceDeleteRequestDto: {}", dto);
        ResponseDto responseBody = placeService.delete(customUserDetails.getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/{id}/bookmarks/status")
    public ResponseEntity<ResponseDto> getBookmarkStatus(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id) {

        ResponseDto responseBody = bookmarkService.checkBookmark(customUserDetails.getId(), id);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    //TODO: domain 위치 확인 Place? Bookmark?
    @GetMapping("/places/bookmarks")
    public ResponseEntity<ResponseDto> getAllBookmarks(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ResponseDto responseBody = bookmarkService.findAllBookmarkedPlace(customUserDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PostMapping("/places/{id}/bookmarks")
    public ResponseEntity<ResponseDto> addBookmark(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id) {

        ResponseDto responseBody = bookmarkService.addBookmark(customUserDetails.getId(), id);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping("/places/{id}/bookmarks")
    public ResponseEntity<ResponseDto> deleteBookmark(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id) {

        ResponseDto responseBody = bookmarkService.deleteBookmark(customUserDetails.getId(), id);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping("/places/bookmarks")
    public ResponseEntity<ResponseDto> deleteSelectedBookmarks(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody DeleteBookmarksRequestDto dto) {

        ResponseDto responseBody = bookmarkService.deleteAllBookmarks(customUserDetails.getId(), dto);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/top3")
    public ResponseEntity<ResponseDto> getTop3Places(@RequestParam(required = false, name = "category") String category) {

        ResponseDto responseBody = placeService.getTop3(category);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}

