package withdog.messaging.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import withdog.messaging.model.place.PlaceActionMessage;
import withdog.messaging.model.place.PlaceFilterMessage;
import withdog.messaging.service.UserMessageProcessingService;
import withdog.messaging.util.constant.KafkaTopics;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventConsumer {

    private final UserMessageProcessingService userMessageProcessingService;

    @KafkaListener(topics = KafkaTopics.PLACE_FILTERS_TOPIC, groupId = "user-event-group")
    public void consumeFilterEvent(@Payload PlaceFilterMessage event) {

        userMessageProcessingService.processFilterEvent(event);
    }

    @KafkaListener(topics = {KafkaTopics.PLACE_VIEWS_TOPIC, KafkaTopics.PLACE_BOOKMARKS_TOPIC}, groupId = "user-event-group")
    public void consumeActionEvent(@Payload PlaceActionMessage event) {

        userMessageProcessingService.processActionEvent(event);

    }
}
