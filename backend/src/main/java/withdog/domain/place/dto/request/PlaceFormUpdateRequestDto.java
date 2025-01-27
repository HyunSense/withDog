package withdog.domain.place.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import withdog.common.validator.NotBlankElements;
import withdog.domain.place.dto.PlaceNewImageDto;
import withdog.domain.place.dto.PlaceUpdateImagesDto;
import withdog.domain.place.entity.Category;
import withdog.domain.place.entity.Place;

import java.util.List;

@Getter
@Setter
@ToString
public class PlaceFormUpdateRequestDto {

    @NotNull(message = "CategoryId must not be null")
    @Min(value = 1, message = "CategoryId must be greater than 0")
    private int categoryId;

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

    @NotBlankElements(message = "Not Blank BlogUrls")
    @Size(max = 4, message = "BlogUrls can have at most {max} items")
    private List<String> blogUrls;

    private List<PlaceNewImageDto> newImages;

    private List<PlaceUpdateImagesDto> updateImages;

    private List<Long> removedImageIds;

    public Place textFieldToEntity(Category category) {
        return Place.builder()
                .category(category)
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
    public PlaceFormUpdateRequestDto(int categoryId, String name, String phone, String addressPart1, String addressPart2, int price, String reservationUrl, String description, List<String> blogUrls, List<PlaceNewImageDto> newImages, List<PlaceUpdateImagesDto> updateImages, List<Long> removedImageIds) {
        this.categoryId = categoryId;
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
