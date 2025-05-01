package withdog.event.model.place;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withdog.event.model.UserEvent;

@Getter
@NoArgsConstructor
public class PlaceDetailViewEvent extends UserEvent {

    private Long placeId;

    @Builder
    public PlaceDetailViewEvent(String sessionId, Long memberId, Long placeId) {
        super("views", sessionId, memberId, System.currentTimeMillis());
        this.placeId = placeId;
    }
}
