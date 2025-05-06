package withdog.domain.place.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class PlaceImageUploadDto {

    private String name;
    private String imageUrl;
    private int imagePosition;


    @Builder
    public PlaceImageUploadDto(String name, String imageUrl, int imagePosition) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.imagePosition = imagePosition;
    }
}
