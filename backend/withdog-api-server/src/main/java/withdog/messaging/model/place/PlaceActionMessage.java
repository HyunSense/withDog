package withdog.messaging.model.place;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withdog.messaging.model.UserMessage;

@Getter
@NoArgsConstructor
public class PlaceActionMessage extends UserMessage {

    private Long placeId;

    @Builder
    public PlaceActionMessage(String eventType, String sessionId, Long memberId, Long placeId) {
        super(eventType, sessionId, memberId, System.currentTimeMillis());
        this.placeId = placeId;
    }
}
