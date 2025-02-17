package withdog.domain.place.entity.filter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withdog.domain.place.entity.Place;

@Getter
@Entity
@IdClass(PlaceFilterId.class)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "place_filters")
public class PlaceFilter {

    @Id
    @JoinColumn(name = "place_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_option_id")
    private FilterOption filterOption;
}
