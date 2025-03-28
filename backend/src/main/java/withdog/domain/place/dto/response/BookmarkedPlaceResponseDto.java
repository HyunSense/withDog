package withdog.domain.place.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class BookmarkedPlaceResponseDto {

    private Long id;
    private String name;
    private String address;
    private String thumbnailUrl;

}
