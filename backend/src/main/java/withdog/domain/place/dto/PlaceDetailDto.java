package withdog.domain.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.PlaceBlog;
import withdog.domain.place.entity.PlaceImage;
import withdog.domain.place.entity.filter.PlaceFilter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlaceDetailDto {

    private Place place;
//    private List<PlaceImage> placeImages;
//    private List<PlaceBlog> placeBlogs;
//    private List<PlaceFilter> placeFilters;
    private PlaceImage placeImage;
    private PlaceBlog placeBlog;
    private PlaceFilter placeFilter;

    public PlaceDetailDto(Place place, PlaceImage placeImage, PlaceBlog placeBlog, PlaceFilter placeFilter) {
        this.place = place;
        this.placeImage = placeImage;
        this.placeBlog = placeBlog;
        this.placeFilter = placeFilter;
    }

//    public PlaceDetailDto(Place place, List<PlaceImage> placeImages, List<PlaceBlog> placeBlogs, List<PlaceFilter> placeFilters) {
//        this.place = place;
//        this.placeImages = placeImages;
//        this.placeBlogs = placeBlogs;
//        this.placeFilters = placeFilters;

//        this.place = place;
//        this.placeImages = placeImages != null ? placeImages : new ArrayList<>();  // null 체크
//        this.placeBlogs = placeBlogs != null ? placeBlogs : new ArrayList<>();  // null 체크
//        this.placeFilters = placeFilters != null ? placeFilters : new ArrayList<>();  // null 체크
//    }
}
