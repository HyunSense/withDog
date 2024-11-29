package withdog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import withdog.entity.Category;
import withdog.entity.Place;
import withdog.validator.NotBlankElements;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TempPlaceRegisterRequestDto {

    @NotBlank(message = "Not Blank CategoryName")
    private String category;

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

    // Custom Validation Annotation
    @NotBlankElements(message = "Not Blank BlogUrls")
    @Size(max = 4, message = "BlogUrls can have at most {max} items")
    private List<String> blogUrls;


//    List<MultipartFile> images;
    // Custom Validation Annotation
//    @NotBlankElements(message = "Not Blank ImageUrls")
//    @Size(max = 4, message = "ImageUrls can have at most {max} items")
//    private List<String> imageUrls;



    @Builder
    public TempPlaceRegisterRequestDto(String category, String name, String phone, String addressPart1, String addressPart2, int price, String reservationUrl, List<String> blogUrls) {
//        this.categoryId = categoryId;
        this.category = category;
        this.name = name;
        this.phone = phone;
        this.addressPart1 = addressPart1;
        this.addressPart2 = addressPart2;
        this.price = price;
        this.reservationUrl = reservationUrl;
        this.blogUrls = blogUrls;
//        this.images = images;
    }

    public Place toEntity(Category category) {

        return Place.builder()
                .category(category)
                .name(this.name)
                .phone(this.phone)
                .addressPart1(this.addressPart1)
                .addressPart2(this.addressPart2)
                .price(this.price)
                .reservationLink(this.reservationUrl)
                .build();
    }
}
