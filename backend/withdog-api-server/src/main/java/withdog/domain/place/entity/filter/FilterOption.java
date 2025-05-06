package withdog.domain.place.entity.filter;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "filter_options")
public class FilterOption {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String value;

    private String displayName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_category_id")
    private FilterCategory filterCategory;
}
