package withdog.messaging.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import withdog.messaging.model.UserMessage;

@RequiredArgsConstructor
@Component
public class PlaceUserEventProducer implements UserEventProducer {

    private final KafkaTemplate<String, UserMessage> kafkaTemplate;


    @Override
    public void send(String topic, String key, UserMessage event) {
        kafkaTemplate.send(topic, key, event);
    }
}
