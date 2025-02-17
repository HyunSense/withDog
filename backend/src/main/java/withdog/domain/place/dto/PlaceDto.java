package withdog.domain.place.dto;

import lombok.*;
import withdog.common.util.StringFilter;
import withdog.domain.place.entity.Place;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class PlaceDto {

    private Long id;
    private String name;
    private String address;
    private int price;
    private String thumbnailUrl;


    public PlaceDto(Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.address = StringFilter.addressSpecialWord(place.getAddressPart1());
        this.price = place.getPrice();
        this.thumbnailUrl = place.getThumbnailUrl();
    }

    public static List<PlaceDto> fromEntityList(List<Place> places) {

        return places.stream().map(p -> new PlaceDto(p)).collect(Collectors.toList());
    }

}
