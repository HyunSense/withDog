package withdog.domain.place.entity.filter;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
public class PlaceFilterId implements Serializable {

    private Long place;
    private Integer filterOption;
}
