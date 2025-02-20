package withdog.domain.place.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import withdog.domain.place.entity.filter.PlaceFilter;
import withdog.domain.stats.entity.PlaceWeeklyStats;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name = "places")
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("imagePosition asc")
    private List<PlaceImage> placeImages = new ArrayList<>();

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceBlog> placeBlogs = new ArrayList<>();

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceWeeklyStats> placeWeeklyStats = new ArrayList<>();

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PlaceFilter> placeFilters = new HashSet<>();

    public void addBlog(PlaceBlog placeBlog) {
        placeBlog.setPlace(this);
        placeBlogs.add(placeBlog);
    }

    public void addImage(PlaceImage placeImage) {
        placeImage.setPlace(this);
        placeImages.add(placeImage);
        this.thumbnailUrl = placeImages.get(0).getImageUrl();
    }

    public void addFilters(Set<PlaceFilter> placeFilter) {
        placeFilters.addAll(placeFilter);
    }

    public void updateFilters(Set<PlaceFilter> placeFilter) {
        placeFilters.clear();
        placeFilters.addAll(placeFilter);
    }

    public void update(Place updatePlace) {
        this.category = updatePlace.getCategory();
        this.name = updatePlace.getName();
        this.price = updatePlace.getPrice();
        this.addressPart1 = updatePlace.getAddressPart1();
        this.addressPart2 = updatePlace.getAddressPart2();
        this.phone = updatePlace.getPhone();
        this.reservationUrl = updatePlace.getReservationUrl();
        this.description = updatePlace.getDescription();
    }

    public void updateThumbnail(PlaceImage placeImage) {
        this.thumbnailUrl = placeImage.getImageUrl();
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
