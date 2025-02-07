package withdog.domain.place.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "place_detail_types")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(PlaceDetailTypeId.class)
public class PlaceDetailType {

    @Id
    @JoinColumn(name = "place_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private Place place;

    @Id
    @JoinColumn(name = "detail_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DetailType detailType;


}
