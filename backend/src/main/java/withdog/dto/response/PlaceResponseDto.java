package withdog.dto.response;

import lombok.*;
import withdog.entity.Place;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceResponseDto {

//    private int categoryId;
    private String category;
    private Long id;
    private String name;
    private String address;
    private int price;
    private String thumbnailUrl;


    @Builder
    public PlaceResponseDto (Place place) {

        this.category = place.getCategory().getName();
        this.id = place.getId();
        this.name = place.getName();
        this.address = place.getAddress();
        this.price = place.getPrice();
        this.thumbnailUrl = place.getThumbnailUrl();
    }
}
