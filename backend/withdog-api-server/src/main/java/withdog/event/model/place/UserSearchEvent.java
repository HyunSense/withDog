package withdog.event.model.place;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withdog.event.model.UserEvent;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class UserSearchEvent extends UserEvent {

    private String keyword;
    private Map<String, List<String>> filters;


    @Builder
    public UserSearchEvent(String sessionId, Long memberId, String keyword, Map<String, List<String>> filters) {
        super("filters", sessionId, memberId, System.currentTimeMillis());
        this.keyword = keyword;
        this.filters = filters;
    }
}

