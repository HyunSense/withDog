package withdog.domain.place.dto;

import lombok.Builder;
import lombok.Getter;
import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.PlaceBlog;

@Getter
public class BlogExtractDto {

    private String title;
    private String description;
    private String imageUrl;

    @Builder
    public BlogExtractDto(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public PlaceBlog toEntity(Place place, String blogUrl) {

        return PlaceBlog.builder()
                .place(place)
                .title(title)
                .description(description)
                .blogUrl(blogUrl)
                .imageUrl(imageUrl)
                .build();
    }
}
