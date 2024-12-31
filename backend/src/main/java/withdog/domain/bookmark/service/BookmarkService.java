package withdog.domain.bookmark.service;

import withdog.common.dto.response.ResponseDto;
import withdog.domain.bookmark.dto.request.DeleteBookmarksRequestDto;

public interface BookmarkService {

    ResponseDto checkBookmark(Long memberId, Long placeId);
    ResponseDto findAllBookmarkedPlace(Long memberId);
    ResponseDto addBookmark(Long memberId, Long placeId);
    ResponseDto deleteBookmark(Long memberId, Long placeId);
    ResponseDto deleteAllBookmarks(Long memberId, DeleteBookmarksRequestDto dto);

}
