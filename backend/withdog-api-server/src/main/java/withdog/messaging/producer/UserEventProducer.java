package withdog.messaging.producer;

import withdog.messaging.model.UserMessage;

public interface UserEventProducer {

    void send(String topic, String key, UserMessage event);
}
