package withdog.stats.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Table(name = "daily_stats")
@Entity
public class DailyStat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String eventType;
    private String metricKey;
    private String metricValue;
    private long count;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public DailyStat(LocalDate date, String eventType, String metricKey, String metricValue, long count) {
        this.date = date;
        this.eventType = eventType;
        this.metricKey = metricKey;
        this.metricValue = metricValue;
        this.count = count;
    }
}
