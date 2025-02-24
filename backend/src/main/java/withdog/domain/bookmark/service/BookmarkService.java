package withdog.domain.bookmark.service;

import withdog.common.dto.response.DataResponseDto;
import withdog.common.dto.response.ResponseDto;
import withdog.domain.bookmark.dto.response.BookmarkStatusDto;
import withdog.domain.place.dto.response.BookmarkedPlaceResponseDto;

import java.util.List;

public interface BookmarkService {

    DataResponseDto<BookmarkStatusDto> checkBookmark(Long memberId, Long placeId);
    DataResponseDto<List<BookmarkedPlaceResponseDto>> findAllBookmarkedPlace(Long memberId);
    ResponseDto addBookmark(Long memberId, Long placeId);
    ResponseDto deleteBookmark(Long memberId, Long placeId);
    ResponseDto deleteAllBookmarks(List<Long> placeIds, Long memberId);

}
