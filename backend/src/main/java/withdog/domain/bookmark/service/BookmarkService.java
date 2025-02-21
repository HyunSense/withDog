package withdog.domain.bookmark.service;

import withdog.common.dto.response.ResponseDto;
import java.util.List;

public interface BookmarkService {

    ResponseDto checkBookmark(Long memberId, Long placeId);
    ResponseDto findAllBookmarkedPlace(Long memberId);
    ResponseDto addBookmark(Long memberId, Long placeId);
    ResponseDto deleteBookmark(Long memberId, Long placeId);
    ResponseDto deleteAllBookmarks(List<Long> placeIds, Long memberId);

}
