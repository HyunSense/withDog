package withdog.event.model.place;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withdog.event.model.UserEvent;

@Getter
@NoArgsConstructor
public class PlaceBookmarkEvent extends UserEvent {

    private Long placeId;

    @Builder
    public PlaceBookmarkEvent(String sessionId, Long memberId, Long placeId) {
        super("bookmarks", sessionId, memberId, System.currentTimeMillis());
        this.placeId = placeId;
    }
}
