package withdog.domain.place.entity.filter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import withdog.domain.place.entity.Place;

//TODO: 수정필요 pk 따로 지정
@Getter
@Entity
//@IdClass(PlaceFilterId.class)
@NoArgsConstructor
@Table(name = "place_filters")
public class PlaceFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "place_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_option_id")
    private FilterOption filterOption;

    public PlaceFilter(Place place, FilterOption filterOption) {
        this.place = place;
        this.filterOption = filterOption;
    }
}
