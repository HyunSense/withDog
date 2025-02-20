package withdog.domain.place.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@ToString
public class PlaceWithFilterDetailResponseDto {


    private String category;
    private Long id;
    private String name;
    private int price;
    private String addressPart1;
    private String addressPart2;
    private String phone;
    private String reservationUrl;
    private List<PlaceBlogDto> placeBlogs;
    private List<PlaceImageDto> placeImages;
    private Map<String, List<String>> filters;


    private PlaceWithFilterDetailResponseDto(Place place, List<PlaceImage> placeImages,
                                             List<PlaceBlog> placeBlogs,
                                             Set<PlaceFilter> placeFilters) {
        this.category = place.getCategory().getName();
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


    public static PlaceWithFilterDetailResponseDto fromEntity(Place place,
                                                              List<PlaceImage> placeImages,
                                                              List<PlaceBlog> placeBlogs,
                                                              Set<PlaceFilter> placeFilters) {

        return new PlaceWithFilterDetailResponseDto(place, placeImages, placeBlogs, placeFilters);
    }
}
