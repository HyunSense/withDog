package withdog.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import withdog.entity.Category;
import withdog.entity.Place;
import withdog.validator.NotBlankElements;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PlaceRegisterRequestDto {

//    @NotNull(message = "Not Blank CategoryId")
//    private int categoryId;

    @NotBlank(message = "Not Blank CategoryName")
    private String categoryName;

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

    // Custom Validation Annotation
    @NotBlankElements(message = "Not Blank BlogUrls")
    @Size(max = 4, message = "BlogUrls can have at most {max} items")
    private List<String> blogUrls;

    // Custom Validation Annotation
    @NotBlankElements(message = "Not Blank ImageUrls")
    @Size(max = 4, message = "ImageUrls can have at most {max} items")
    private List<String> imageUrls;

    @Builder
    public PlaceRegisterRequestDto(String categoryName, String name, String phone, String addressPart1, String addressPart2, int price, String reservationUrl, List<String> blogUrls, List<String> imageUrls) {
//        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.name = name;
        this.phone = phone;
        this.addressPart1 = addressPart1;
        this.addressPart2 = addressPart2;
        this.price = price;
        this.reservationUrl = reservationUrl;
        this.blogUrls = blogUrls;
        this.imageUrls = imageUrls;
    }

    public Place toEntity(Category category) {

        return Place.builder()
                .category(category)
                .name(this.name)
                .phone(this.phone)
                .addressPart1(this.addressPart1)
                .addressPart2(this.addressPart2)
                .price(this.price)
                .reservationUrl(this.reservationUrl)
                .build();
    }
}
