package withdog.domain.bookmark.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkStatusDto {

    private boolean bookmarked;

    @Builder
    public BookmarkStatusDto(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

}
