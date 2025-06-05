package withdog.domain.place.dto.response;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import withdog.domain.place.entity.Place;
import withdog.common.util.StringFilter;
import withdog.domain.stats.entity.PlaceWeeklyStats;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PlaceResponseDto {

    private Long id;
    private String name;
    private String address;
    private int price;
    private String thumbnailUrl;

    public PlaceResponseDto (Place place) {

        this.id = place.getId();
        this.name = place.getName();
        this.address = StringFilter.addressSpecialWord(place.getAddressPart1());
        this.price = place.getPrice();
        this.thumbnailUrl = place.getThumbnailUrl();
    }

    public static Slice<PlaceResponseDto> fromEntitySlice(Slice<Place> places) {
        return places.map(p -> new PlaceResponseDto(p));
    }

    public static Page<PlaceResponseDto> fromEntityPage(Page<Place> places) {
        return places.map(p -> new PlaceResponseDto(p));
    }

    public static List<PlaceResponseDto> fromEntityPlaceWeeklyStatsList(List<PlaceWeeklyStats> placeWeeklyStatsList) {
        return placeWeeklyStatsList.stream().map(p -> new PlaceResponseDto(p.getPlace())).collect(Collectors.toList());
    }

    public static List<PlaceResponseDto> fromEntityList(List<Place> places) {

        return places.stream().map(p -> new PlaceResponseDto(p)).collect(Collectors.toList());
    }
}
