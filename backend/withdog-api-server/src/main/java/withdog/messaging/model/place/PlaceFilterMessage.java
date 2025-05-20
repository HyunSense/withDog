package withdog.messaging.model.place;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withdog.messaging.model.UserMessage;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class PlaceFilterMessage extends UserMessage {

    private String keyword;
    private Map<String, List<String>> filters;

    @Builder
    public PlaceFilterMessage(String sessionId, Long memberId, String keyword, Map<String, List<String>> filters) {
        super("filters", sessionId, memberId, System.currentTimeMillis());
        this.keyword = keyword;
        this.filters = filters;
    }
}