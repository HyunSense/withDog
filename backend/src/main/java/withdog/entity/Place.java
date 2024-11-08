package withdog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "places")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;
    private String thumbnailUrl;
    private int price;
    private String address;
    private String phone;
    private String reservationLink;
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //TODO: orphanRemoval = true 고려
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceImage> placeImages = new ArrayList<>();

    public void addImage(PlaceImage placeImage) {
        placeImage.setPlace(this);
        placeImages.add(placeImage);
        if (placeImages != null && !placeImages.isEmpty()) {
            this.thumbnailUrl = placeImages.get(0).getImageUrl();
        }
    }

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceBlog> placeBlogs = new ArrayList<>();

    public void addBlog(PlaceBlog placeBlog) {
        placeBlogs.add(placeBlog);
        placeBlog.setPlace(this);
    }

    @Builder
    public Place(Long id, Category category, String name, String thumbnailUrl, int price, String address, String phone, String reservationLink, String description) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.price = price;
        this.address = address;
        this.phone = phone;
        this.reservationLink = reservationLink;
        this.description = description;
    }
}
