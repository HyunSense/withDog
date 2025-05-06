package withdog.event.publisher;

import withdog.event.model.UserEvent;

public interface UserEventPublisher {

    void publish(String topic, String key, UserEvent event);
}
