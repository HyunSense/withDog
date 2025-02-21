package withdog.domain.place.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PlaceSearchRequestDto {

    private String keyword;
    private List<String> types;
    private List<String> city;
    private List<String> petAccessTypes;
    private List<String> petSizes;
    private List<String> services;
}
