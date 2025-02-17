package withdog.domain.place.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "place_images")
public class PlaceImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 하나의 장소가 여러개의 이미지를 가질 수 있음
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private String name;

    private String imageUrl;

    private int imagePosition;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public PlaceImage(Place place, String name, String imageUrl, int imagePosition) {

        this.place = place;
        this.name = name;
        this.imageUrl = imageUrl;
        this.imagePosition = imagePosition;
    }

    public void update(PlaceImage updatePlaceImage) {
        this.place = updatePlaceImage.getPlace();
        this.name = updatePlaceImage.getName();
        this.imageUrl = updatePlaceImage.getImageUrl();
        this.imagePosition = updatePlaceImage.getImagePosition();
    }

    public void updatePosition(int imagePosition) {
        this.imagePosition = imagePosition;
    }

//    public static PlaceImageDto toPlaceImageDto(PlaceImage placeImage) {
//
//        return PlaceImageDto.builder()
//                .imagePosition(placeImage.getImagePosition())
//                .imageUrl(placeImage.getImageUrl())
//                .build();
//    }
}
