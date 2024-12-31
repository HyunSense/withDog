package withdog.domain.bookmark.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DeleteBookmarksRequestDto {

    private List<Long> bookmarkPlaceIds;

}