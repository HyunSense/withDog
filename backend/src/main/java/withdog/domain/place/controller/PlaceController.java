package withdog.domain.place.controller;

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
import withdog.common.config.auth.CustomUserDetails;
import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.domain.bookmark.dto.request.DeleteBookmarksRequestDto;
import withdog.domain.bookmark.service.BookmarkService;
import withdog.domain.place.dto.request.PlaceDeleteRequestDto;
import withdog.domain.place.dto.request.PlaceFormRequestDto;
import withdog.domain.place.dto.request.PlaceFormUpdateRequestDto;
import withdog.domain.place.dto.request.PlaceSearchRequestDto;
import withdog.domain.place.dto.response.PlaceDetailResponseDto;
import withdog.domain.place.dto.response.PlaceResponseDto;
import withdog.domain.place.service.PlaceService;

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

        log.info("PlaceFormRequestDto: {}", dto);

        ResponseDto responseBody = placeService.save(dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places")
    public ResponseEntity<DataResponseDto<Slice<PlaceResponseDto>>> getAllPlaces(@RequestParam(required = false, defaultValue = "0") int categoryId, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        log.info("pageable : {}", pageable.toString());
        DataResponseDto<Slice<PlaceResponseDto>> responseBody = placeService.findAllPlace(categoryId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/types")
    public ResponseEntity<ResponseDto> getPlaceTypes(@RequestParam List<String> types, @RequestParam int limit) {

        
        return null;
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
    public ResponseEntity<ResponseDto> getTop3Places(@RequestParam(required = false, defaultValue = "0") int categoryId) {

        ResponseDto responseBody = placeService.getTop3(categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/search")
    public ResponseEntity<DataResponseDto<Slice<PlaceResponseDto>>> getSearchPlace(@RequestParam(defaultValue = "name") String type, @RequestParam(required = false ,defaultValue = "") String keyword, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        DataResponseDto<Slice<PlaceResponseDto>> responseBody = placeService.searchPlace(type, keyword, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/recent")
    public ResponseEntity<ResponseDto> getRecentPlaces(@RequestParam(name = "size") int limit) {

        int maxLimit = Math.min(limit, 10);
        ResponseDto responseBody = placeService.recentPlaces(maxLimit);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/random")
    public ResponseEntity<ResponseDto> getRandomPlace(@RequestParam(name = "size") int limit) {

        int maxLimit = Math.min(limit, 10);
        ResponseDto responseBody = placeService.randomPlaces(maxLimit);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }


    @GetMapping("/places/search/result")
    public ResponseEntity<ResponseDto> getSearchFilter(@ModelAttribute PlaceSearchRequestDto dto, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        log.info("placeSearchRequestDto: {}", dto.toString());
        log.info("pageable [OFFSET: {}] [SIZE: {}]", pageable.getOffset(), pageable.getPageSize());

        ResponseDto responseBody = placeService.searchFilterPlace(dto, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}

