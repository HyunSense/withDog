package withdog.domain.place.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "place_blogs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceBlog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 하나의 장소는 여러개의 블로그 주소들을 가질수 있음 -> ManyToOne(다대일)
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private String title;

    private String blogUrl;

    private String imageUrl;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public PlaceBlog(Place place, String title, String blogUrl, String imageUrl, String description) {
        this.place = place;
        this.title = title;
        this.blogUrl = blogUrl;
        this.imageUrl = imageUrl;
        this.description = description;
    }
}
