package withdog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import withdog.entity.PlaceImage;

@Getter
@Setter
@ToString
public class PlaceImageDto {

    private String imageUrl;
    private int imagePosition;


    @Builder
    public PlaceImageDto(PlaceImage placeImage) {

        this.imageUrl = placeImage.getImageUrl();
        this.imagePosition = placeImage.getImagePosition();
    }
}
