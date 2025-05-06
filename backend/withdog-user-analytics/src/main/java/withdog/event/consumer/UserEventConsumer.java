package withdog.event.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import withdog.event.model.place.PlaceActionEvent;
import withdog.event.model.place.PlaceFilterEvent;
import withdog.event.service.UserEventProcessingService;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventConsumer {

    private final UserEventProcessingService userEventProcessingService;

    @KafkaListener(topics = "place-filters", groupId = "user-event-group")
    public void consumeFilterEvent(@Payload PlaceFilterEvent event) {

        userEventProcessingService.processFilterEvent(event);
    }

    @KafkaListener(topics = {"place-views", "place-bookmarks"}, groupId = "user-event-group")
    public void consumeActionEvent(@Payload PlaceActionEvent event) {

        userEventProcessingService.processActionEvent(event);

    }
}
