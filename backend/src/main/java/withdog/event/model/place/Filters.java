package withdog.event.model.place;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Filters {

    private List<String> types;
    private List<String> city;
    private List<String> petAccessTypes;
    private List<String> petSizes;
    private List<String> services;

}
