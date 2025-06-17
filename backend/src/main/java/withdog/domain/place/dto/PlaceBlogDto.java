package withdog.domain.place.dto;

import lombok.*;
import withdog.domain.place.entity.PlaceBlog;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceBlogDto {

    private String title;
    private String description;
    private String blogUrl;
    private String imageUrl;

    @Builder
    public PlaceBlogDto(PlaceBlog placeBlog) {
        this.title = placeBlog.getTitle();
        this.description = placeBlog.getDescription();
        this.blogUrl = placeBlog.getBlogUrl();
        this.imageUrl = placeBlog.getImageUrl();
    }
}
