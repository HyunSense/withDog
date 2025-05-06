package withdog.domain.place.entity.filter;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "filter_categories")
public class FilterCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String displayName;
}
