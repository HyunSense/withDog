package withdog.domain.place.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
public class PlaceDetailTypeId implements Serializable {

    private Long place;
    private int detailType;
}
