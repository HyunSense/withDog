package withdog.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PopularPlaceDto {

    private String placeId;
    private Long viewCount;
    private Long bookmarkCount;
    private Long popularScore;

}
