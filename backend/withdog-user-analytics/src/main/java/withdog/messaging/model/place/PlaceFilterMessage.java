package withdog.messaging.model.place;

import lombok.*;
import withdog.messaging.model.UserMessage;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class PlaceFilterMessage extends UserMessage {

    private String keyword;
    private Map<String, List<String>> filters;

}