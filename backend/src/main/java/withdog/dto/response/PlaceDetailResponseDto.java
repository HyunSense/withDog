package withdog.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import withdog.dto.PlaceBlogDto;
import withdog.dto.PlaceImageDto;
import withdog.entity.Place;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class PlaceDetailResponseDto {

    private String category;
    private String name;
    private int price;
    private String address;
    private String phone;
    private String reservationLink;
    private List<PlaceImageDto> placeImages;
    private List<PlaceBlogDto> placeBlogs;


    @Builder
    public PlaceDetailResponseDto(Place place) {

        this.category = place.getCategory().getName();
        this.name = place.getName();
        this.price = place.getPrice();
        this.address = place.getAddress();
        this.phone = place.getPhone();
        this.reservationLink = place.getReservationLink();
//        this.placeImages = placeImages;
//        this.placeBlogs = placeBlogs;
        //TODO: !isEmpty if문 넣을지 고민
//        if (!place.getPlaceImages().isEmpty()){
            this.placeImages = place.getPlaceImages().stream()
                    .map(i -> PlaceImageDto.builder().placeImage(i).build()).collect(Collectors.toList());
//        }
//        if (!place.getPlaceBlogs().isEmpty()){
            this.placeBlogs = place.getPlaceBlogs().stream()
                    .map(b -> PlaceBlogDto.builder().placeBlog(b).build()).collect(Collectors.toList());
//        }
    }
}
