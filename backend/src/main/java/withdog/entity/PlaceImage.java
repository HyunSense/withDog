package withdog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import withdog.dto.PlaceImageDto;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "place_images")
public class PlaceImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 하나의 장소가 여러개의 이미지를 가질 수 있음
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "place_id")
    private Place place;

    private String imageUrl;

    private int imagePosition;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public PlaceImage(Place place, String imageUrl, int imagePosition) {

        this.place = place;
        this.imageUrl = imageUrl;
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
