package withdog.domain.place.dto;

import lombok.*;
import withdog.domain.place.entity.PlaceImage;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PlaceImageDto {

    private Long id;
    private String name;
    private String imageUrl;
    private int imagePosition;


    @Builder
    public PlaceImageDto(PlaceImage placeImage) {

        this.id = placeImage.getId();
        this.name = placeImage.getName();
        this.imageUrl = placeImage.getImageUrl();
        this.imagePosition = placeImage.getImagePosition();
    }
}
