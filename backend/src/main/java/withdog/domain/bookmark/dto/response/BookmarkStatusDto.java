package withdog.domain.bookmark.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkStatusDto {

    private boolean bookmarked;

    @Builder
    public BookmarkStatusDto(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

}
