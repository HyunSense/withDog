package withdog.event.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import withdog.repository.DailyStatRepository;
import withdog.stats.dto.PopularPlaceDto;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventProducer {

    private final DailyStatRepository dailyStatRepository;
    private final KafkaTemplate<String, List<PopularPlaceDto>> kafkaTemplate;

    public void publishPlaceStat() {

        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now().minusDays(1);

        List<PopularPlaceDto> popularPlaces = dailyStatRepository.findPopularPlaces(startDate, endDate);

        kafkaTemplate.send("place-stat", popularPlaces);

    }
}
