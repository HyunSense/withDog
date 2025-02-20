package withdog.domain.place.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import withdog.domain.place.entity.filter.PlaceFilter;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class FilterOptionAndCategoryDto {

    private String name;
    private List<String> value;

}
