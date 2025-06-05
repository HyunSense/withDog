package withdog.domain.place.dto.response;

import lombok.*;
import withdog.domain.place.dto.PlaceBlogDto;
import withdog.domain.place.dto.PlaceImageDto;
import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.PlaceBlog;
import withdog.domain.place.entity.PlaceImage;
import withdog.domain.place.entity.filter.PlaceFilter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PlaceDetailResponseDto {

    private Long id;
    private String name;
    private int price;
    private String addressPart1;
    private String addressPart2;
    private String phone;
    private String reservationUrl;
    private List<PlaceImageDto> placeImages;
    private List<PlaceBlogDto> placeBlogs;
    private Map<String, List<String>> filters;



    private PlaceDetailResponseDto(Place place, List<PlaceImage> placeImages,
                                   List<PlaceBlog> placeBlogs,
                                   Set<PlaceFilter> placeFilters) {

        this.id = place.getId();
        this.name = place.getName();
        this.price = place.getPrice();
        this.addressPart1 = place.getAddressPart1();
        this.addressPart2 = place.getAddressPart2();
        this.phone = place.getPhone();
        this.reservationUrl = place.getReservationUrl();
        this.placeImages = placeImages.stream()
                .map(pi -> PlaceImageDto.builder().placeImage(pi).build()).collect(Collectors.toList());
        this.placeBlogs = placeBlogs.stream()
                .map(pb -> PlaceBlogDto.builder().placeBlog(pb).build()).collect(Collectors.toList());
        this.filters = placeFilters.stream()
                .collect(Collectors.groupingBy(pf -> pf.getFilterOption().getFilterCategory().getName(),
                        Collectors.mapping(pf -> pf.getFilterOption().getValue(), Collectors.toList())));
    }


    public static PlaceDetailResponseDto fromEntity(Place place, List<PlaceImage> placeImages,
                                                    List<PlaceBlog> placeBlogs,
                                                    Set<PlaceFilter> placeFilters) {
        return new PlaceDetailResponseDto(place, placeImages, placeBlogs, placeFilters);
    }
}
