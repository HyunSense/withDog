package withdog.domain.stats.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withdog.domain.place.entity.Place;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "place_weekly_stats")
@NoArgsConstructor
public class PlaceWeeklyStats {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;
    private LocalDate weekStartDate;
    private Long hitCount;
    private Long bookmarkCount;

    @PrePersist
    private void prePersist() {
        if (hitCount == null) {
            hitCount = 0L;
        }
        if (bookmarkCount == null) {
            bookmarkCount = 0L;
        }
    }

    public void increaseHitCount() {
        hitCount++;
    }

    public void increaseBookmarkCount() {
        bookmarkCount++;
    }

    public void decreaseBookmarkCount() {
        if (bookmarkCount > 0) {
            bookmarkCount--;
        }
    }

    @Builder
    public PlaceWeeklyStats(Place place, LocalDate weekStartDate) {
        this.place = place;
        this.weekStartDate = weekStartDate;
    }
}
