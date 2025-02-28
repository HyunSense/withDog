package withdog.domain.place.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import withdog.common.validator.ValidUrl;
import withdog.domain.place.dto.PlaceNewImageDto;
import withdog.domain.place.dto.PlaceUpdateImagesDto;
import withdog.domain.place.entity.Place;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class PlaceFormUpdateRequestDto {

    private Map<String, List<String>> filters;

    @NotBlank(message = "Not Blank Name")
    private String name;

    private String phone;

    @NotBlank(message = "Not Blank Address")
    private String addressPart1;

    @NotBlank(message = "Not Blank Address")
    private String addressPart2;

    @NotNull(message = "Not Blank Price")
    private int price;

    private String reservationUrl;

    private String description;

    @ValidUrl(message = "Not Blank BlogUrls")
    @Size(max = 4, message = "BlogUrls can have at most {max} items")
    private List<String> blogUrls;

    private List<PlaceNewImageDto> newImages;

    private List<PlaceUpdateImagesDto> updateImages;

    private List<Long> removedImageIds;

    public Place textFieldToEntity() {
        return Place.builder()
                .name(this.getName())
                .price(this.getPrice())
                .addressPart1(this.getAddressPart1())
                .addressPart2(this.getAddressPart2())
                .phone(this.getPhone())
                .reservationUrl(this.getReservationUrl())
                .description(this.getDescription())
                .build();
    }

    @Builder
    public PlaceFormUpdateRequestDto(String name, String phone, String addressPart1, String addressPart2, int price, String reservationUrl, String description, List<String> blogUrls, List<PlaceNewImageDto> newImages, List<PlaceUpdateImagesDto> updateImages, List<Long> removedImageIds) {
        this.name = name;
        this.phone = phone;
        this.addressPart1 = addressPart1;
        this.addressPart2 = addressPart2;
        this.price = price;
        this.reservationUrl = reservationUrl;
        this.description = description;
        this.blogUrls = blogUrls;
        this.newImages = newImages;
        this.updateImages = updateImages;
        this.removedImageIds = removedImageIds;
    }
}
