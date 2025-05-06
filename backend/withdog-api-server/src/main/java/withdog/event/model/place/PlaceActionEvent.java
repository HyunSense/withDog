package withdog.event.model.place;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withdog.event.model.UserEvent;

@Getter
@NoArgsConstructor
public class PlaceActionEvent extends UserEvent {

    private Long placeId;

    @Builder
    public PlaceActionEvent(String eventType, String sessionId, Long memberId, Long placeId) {
        super(eventType, sessionId, memberId, System.currentTimeMillis());
        this.placeId = placeId;
    }
}
