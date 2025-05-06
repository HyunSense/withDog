package withdog.event.model.place;

import lombok.*;
import withdog.event.model.UserEvent;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class PlaceFilterEvent extends UserEvent {

    private String keyword;
    private Map<String, List<String>> filters;

}