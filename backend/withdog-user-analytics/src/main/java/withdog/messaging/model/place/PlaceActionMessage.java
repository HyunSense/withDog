package withdog.messaging.model.place;

import lombok.Getter;
import lombok.NoArgsConstructor;
import withdog.messaging.model.UserMessage;

@Getter
@NoArgsConstructor
public class PlaceActionMessage extends UserMessage {

    private Long placeId;

}
