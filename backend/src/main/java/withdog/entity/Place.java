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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;
    private String thumbnailUrl;
    private int price;
    private String addressPart1;
    private String addressPart2;
    private String phone;
    private String reservationUrl;
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //TODO: orphanRemoval = true 고려, ALL, REMOVE 차이를 둔 이유?
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceImage> placeImages = new ArrayList<>();

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceBlog> placeBlogs = new ArrayList<>();

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceWeeklyStats> placeWeeklyStats = new ArrayList<>();

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "place", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    //TODO: 연관된 엔티티(PlaceBlog, PlaceImage) 에서는 Place Entity를 Setter로 사용해도되는지?
    public void addBlog(PlaceBlog placeBlog) {
        placeBlogs.add(placeBlog);
        placeBlog.setPlace(this);
    }

    public void addImage(PlaceImage placeImage) {
        placeImage.setPlace(this);
        placeImages.add(placeImage);
        this.thumbnailUrl = placeImages.get(0).getImageUrl();
    }

    public String getFullAddress() {
        return addressPart1 + " " + addressPart2;
    }

    public void update(Place updatePlace) {
        this.category = updatePlace.getCategory();
        this.name = updatePlace.getName();
//        this.thumbnailUrl = place.getThumbnailUrl();
        this.price = updatePlace.getPrice();
        this.addressPart1 = updatePlace.getAddressPart1();
        this.addressPart2 = updatePlace.getAddressPart2();
        this.phone = updatePlace.getPhone();
        this.reservationUrl = updatePlace.getReservationUrl();
        this.description = updatePlace.getDescription();
    }

    @Builder
    public Place(Long id, Category category, String name, String thumbnailUrl, int price, String addressPart1, String addressPart2, String phone, String reservationUrl, String description) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.price = price;
        this.addressPart1 = addressPart1;
        this.addressPart2 = addressPart2;
        this.phone = phone;
        this.reservationUrl = reservationUrl;
        this.description = description;
    }
}
