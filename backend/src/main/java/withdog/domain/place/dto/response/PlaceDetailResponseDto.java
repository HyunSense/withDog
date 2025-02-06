package withdog.domain.place.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import withdog.domain.place.dto.PlaceBlogDto;
import withdog.domain.place.dto.PlaceImageDto;
import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.PlaceBlog;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class PlaceDetailResponseDto {

    private String category;
    private Long id;
    private String name;
    private int price;
    private String addressPart1;
    private String addressPart2;
    private String phone;
    private String reservationUrl;
    private List<PlaceImageDto> placeImages;
    private List<PlaceBlogDto> placeBlogs;


//    @Builder
    private PlaceDetailResponseDto(Place place, List<PlaceBlog> placeBlogs) {

        this.category = place.getCategory().getName();
        this.id = place.getId();
        this.name = place.getName();
        this.price = place.getPrice();
        this.addressPart1 = place.getAddressPart1();
        this.addressPart2 = place.getAddressPart2();
        this.phone = place.getPhone();
        this.reservationUrl = place.getReservationUrl();
        this.placeImages = place.getPlaceImages().stream()
                .map(i -> PlaceImageDto.builder().placeImage(i).build()).collect(Collectors.toList());
//        this.placeBlogs = place.getPlaceBlogs().stream() // Lazy 로딩 발생
//                .map(b -> PlaceBlogDto.builder().placeBlog(b).build()).collect(Collectors.toList());
        this.placeBlogs = placeBlogs.stream()
                .map(b -> PlaceBlogDto.builder().placeBlog(b).build()).collect(Collectors.toList());
    }


    public static PlaceDetailResponseDto fromEntity(Place place, List<PlaceBlog> placeBlogs) {
        return new PlaceDetailResponseDto(place, placeBlogs);
    }
}
