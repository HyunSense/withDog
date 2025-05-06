package withdog.event.model.place;

import lombok.Getter;
import lombok.NoArgsConstructor;
import withdog.event.model.UserEvent;

@Getter
@NoArgsConstructor
public class PlaceActionEvent extends UserEvent {

    private Long placeId;

}
