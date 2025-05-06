//package withdog.event.consumer;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//import withdog.event.model.place.PlaceBookmarkEvent;
//import withdog.event.model.place.PlaceDetailViewEvent;
//import withdog.event.model.place.UserSearchEvent;
//import withdog.event.service.UserEventProcessingService;
//
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class UserEventConsumer2 {
//
//    private final UserEventProcessingService userEventService;
//
//    @KafkaListener(topics = "place-filters", groupId = "user-event-group")
//    public void consumeSearchEvent(@Header(KafkaHeaders.RECEIVED_KEY) String key, @Payload UserSearchEvent event) {
//        userEventService.processUserPlaceSearchEvent(key, event);
//    }
//
//    @KafkaListener(topics = "place-views", groupId = "user-event-group")
//    public void consumeViewEvent(@Header(KafkaHeaders.RECEIVED_TOPIC) String key, @Payload PlaceDetailViewEvent event) {
//
//        userEventService.processUserPlaceDetailViewEvent(key, event);
//    }
//
//    @KafkaListener(topics = "place-bookmarks", groupId = "user-event-group")
//    public void consumeBookmarkEvent(@Header(KafkaHeaders.RECEIVED_TOPIC) String key, @Payload PlaceBookmarkEvent event) {
//
//        userEventService.processUserPlaceBookmarkEvent(key, event);
//
//    }
//}
