package withdog.event.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserEvent {

    private String eventType;
    private Long timestamp;
    private String sessionId;
    private Long memberId;

    public UserEvent(String eventType, String sessionId, Long memberId, Long timestamp) {
        this.eventType = eventType;
        this.sessionId = sessionId;
        this.memberId = memberId;
        this.timestamp = timestamp;
    }
}