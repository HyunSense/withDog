package withdog.domain.place.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import withdog.domain.place.entity.PlaceBlog;

@Getter
@Setter
@ToString
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
