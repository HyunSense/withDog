package withdog.domain.place.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import withdog.domain.place.dto.PlaceDto;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PlaceTypeResponseDto {

    private String type;
    private List<PlaceDto> places;

}
