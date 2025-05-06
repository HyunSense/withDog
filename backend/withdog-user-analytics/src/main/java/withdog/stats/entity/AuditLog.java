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
@Table(name = "audit_logs")
@Entity
public class AuditLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskName;
    private LocalDate taskDate;
    private String status;
    private String message;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public AuditLog(String taskName, LocalDate taskDate, String status, String message) {
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.status = status;
        this.message = message;
    }
}
