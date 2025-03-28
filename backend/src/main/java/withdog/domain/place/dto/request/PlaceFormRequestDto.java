package withdog.domain.place.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import withdog.common.validator.ValidUrl;
import withdog.domain.place.dto.PlaceNewImageDto;
import withdog.domain.place.entity.Place;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PlaceFormRequestDto {

    private Map<String, List<String>> filters;

    @NotBlank(message = "Not Blank Name")
    private String name;

    private String phone;

    @NotBlank(message = "Not Blank Address")
    private String addressPart1;

    @NotBlank(message = "Not Blank Address")
    private String addressPart2;

    @NotNull(message = "Not Blank Price")
    private Integer price;

    private String reservationUrl;

    private String description;

    // Custom Annotation
    @ValidUrl
    @Size(max = 4, message = "BlogUrls can have at most {max} items")
    private List<String> blogUrls;

    private List<PlaceNewImageDto> images;

    @Builder
    public PlaceFormRequestDto(String name, String phone, String addressPart1, String addressPart2, int price, String reservationUrl, String description, List<String> blogUrls, List<PlaceNewImageDto> images) {
        this.name = name;
        this.phone = phone;
        this.addressPart1 = addressPart1;
        this.addressPart2 = addressPart2;
        this.price = price;
        this.reservationUrl = reservationUrl;
        this.description = description;
        this.blogUrls = blogUrls;
        this.images = images;
    }

    public Place toEntity() {

        return Place.builder()
                .name(this.name)
                .phone(this.phone)
                .addressPart1(this.addressPart1)
                .addressPart2(this.addressPart2)
                .price(this.price)
                .description(this.description)
                .reservationUrl(this.reservationUrl)
                .build();
    }
}
