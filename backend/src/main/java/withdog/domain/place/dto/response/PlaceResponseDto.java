package withdog.domain.place.dto.response;

import lombok.*;
import org.springframework.data.domain.Slice;
import withdog.domain.place.entity.Place;
import withdog.common.util.StringFilter;
import withdog.domain.stats.entity.PlaceWeeklyStats;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceResponseDto {

    private String category;
    private Long id;
    private String name;
    private String address;
    private int price;
    private String thumbnailUrl;

//    @Builder
    public PlaceResponseDto (Place place) {

        this.category = place.getCategory().getName();
        this.id = place.getId();
        this.name = place.getName();
        this.address = StringFilter.addressSpecialWord(place.getAddressPart1());
        this.price = place.getPrice();
        this.thumbnailUrl = place.getThumbnailUrl();
    }

    public static Slice<PlaceResponseDto> fromEntitySlice(Slice<Place> places) {
        return places.map(p -> new PlaceResponseDto(p));
    }

    public static List<PlaceResponseDto> fromEntityList(List<PlaceWeeklyStats> placeWeeklyStatsList) {
        return placeWeeklyStatsList.stream().map(p -> new PlaceResponseDto(p.getPlace())).collect(Collectors.toList());
    }
}
