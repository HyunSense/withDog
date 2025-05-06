package withdog.domain.place.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import withdog.common.config.auth.CustomUserDetails;
import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.common.dto.response.SliceResponseDto;
import withdog.common.resolver.annotation.SessionId;
import withdog.domain.bookmark.dto.response.BookmarkStatusDto;
import withdog.domain.bookmark.service.BookmarkService;
import withdog.domain.place.dto.request.PlaceFormRequestDto;
import withdog.domain.place.dto.request.PlaceFormUpdateRequestDto;
import withdog.domain.place.dto.request.PlaceSearchRequestDto;
import withdog.domain.place.dto.response.BookmarkedPlaceResponseDto;
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
    //TODO: @RequestPart 추후 변경 고려
    //TODO: 모든 메서드 @AuthenticationPrincipal 의 expression 적용 고려
    @PostMapping("/places")
    public ResponseEntity<ResponseDto> savePlace(@Valid @ModelAttribute PlaceFormRequestDto dto) {

        log.info("PlaceFormRequestDto: {}", dto);
        ResponseDto responseBody = placeService.save(dto);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places")
    public ResponseEntity<ResponseDto> getAllPlaces(@PageableDefault(page = 0, size = 10) Pageable pageable) {

        log.info("Received request with page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        DataResponseDto<SliceResponseDto<PlaceResponseDto>> responseBody = placeService.findAllPlace(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }


    @GetMapping("/places/{id}")
    public ResponseEntity<ResponseDto> getPlace(@PathVariable Long id, @AuthenticationPrincipal(expression = "id") Long memberId, @SessionId String sessionId) {

        DataResponseDto<PlaceDetailResponseDto> responseBody = placeService.findPlace(id, sessionId, memberId);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/admin/places/{id}")
    public ResponseEntity<ResponseDto> getAdminPlace(@PathVariable Long id) {

        DataResponseDto<PlaceDetailResponseDto> responseBody = placeService.findPlaceForUpdate(id);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PutMapping("/places/{id}")
    public ResponseEntity<ResponseDto> updatePlace(@PathVariable Long id, @Valid @ModelAttribute PlaceFormUpdateRequestDto dto) {

        log.info("PlaceUpdateRequestDto: {}", dto);
        ResponseDto responseBody = placeService.update(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping("/places")
    public ResponseEntity<ResponseDto> deletePlace(@RequestParam List<Long> ids) {

        log.info("ids: {}", ids);
        ResponseDto responseBody = placeService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/{id}/bookmarks/status")
    public ResponseEntity<ResponseDto> getBookmarkStatus(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id) {

        DataResponseDto<BookmarkStatusDto> responseBody = bookmarkService.checkBookmark(customUserDetails.getId(), id);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/bookmarks")
    public ResponseEntity<ResponseDto> getAllBookmarks(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        DataResponseDto<List<BookmarkedPlaceResponseDto>> responseBody = bookmarkService.findAllBookmarkedPlace(customUserDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PostMapping("/places/{id}/bookmarks")
    public ResponseEntity<ResponseDto> addBookmark(@AuthenticationPrincipal(expression = "id") Long memberId, @SessionId String sessionId, @PathVariable Long id) {

        ResponseDto responseBody = bookmarkService.addBookmark(memberId, sessionId, id);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping("/places/{id}/bookmarks")
    public ResponseEntity<ResponseDto> deleteBookmark(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id) {

        ResponseDto responseBody = bookmarkService.deleteBookmark(customUserDetails.getId(), id);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping("/places/bookmarks")
    public ResponseEntity<ResponseDto> deleteSelectedBookmarks(@RequestParam List<Long> ids, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ResponseDto responseBody = bookmarkService.deleteAllBookmarks(ids, customUserDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/top3")
    public ResponseEntity<ResponseDto> getTop3Places() {

        DataResponseDto<List<PlaceResponseDto>> responseBody = placeService.getTop3();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/recent")
    public ResponseEntity<ResponseDto> getRecentPlaces(@RequestParam(name = "size") int limit) {

        int maxLimit = Math.min(limit, 10);
        DataResponseDto<List<PlaceResponseDto>> responseBody = placeService.recentPlaces(maxLimit);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/random")
    public ResponseEntity<ResponseDto> getRandomPlace(@RequestParam(name = "size") int limit) {

        int maxLimit = Math.min(limit, 10);
        DataResponseDto<List<PlaceResponseDto>> responseBody = placeService.randomPlaces(maxLimit);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }


    @GetMapping("/places/search/result")
    public ResponseEntity<ResponseDto> getSearchFilter(@ModelAttribute PlaceSearchRequestDto dto, @PageableDefault(page = 0, size = 10) Pageable pageable,
                                                       @AuthenticationPrincipal(expression = "id") Long memberId, @SessionId String sessionId) {

        log.info("pageable [OFFSET: {}] [SIZE: {}]", pageable.getOffset(), pageable.getPageSize());

        DataResponseDto<SliceResponseDto<PlaceResponseDto>> responseBody = placeService.searchFilterPlace(dto, pageable, sessionId, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/search/result/count")
    public ResponseEntity<ResponseDto> getSearchFilterCount(@ModelAttribute PlaceSearchRequestDto dto) {

        DataResponseDto<Long> responseBody = placeService.searchFilterCountPlaces(dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/places/count")
    public ResponseEntity<ResponseDto> getCountPlaces() {

        DataResponseDto<Long> dto = placeService.countPlaces();

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}

