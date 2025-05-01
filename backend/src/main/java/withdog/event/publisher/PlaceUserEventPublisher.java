package withdog.event.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import withdog.event.model.UserEvent;

@RequiredArgsConstructor
@Component
public class PlaceUserEventPublisher implements UserEventPublisher {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;


    @Override
    public void publish(String topic, String key, UserEvent event) {
        kafkaTemplate.send(topic, key, event);
    }
}
