package withdog.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
//@NoArgsConstructor
public class PlaceUpdateImagesDto {

    private List<PlaceImagePositionDto> images;
    private List<Long> removedId;

}
