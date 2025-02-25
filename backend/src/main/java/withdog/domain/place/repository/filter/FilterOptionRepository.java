package withdog.domain.place.repository.filter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import withdog.domain.place.entity.filter.FilterOption;

import java.util.List;

@Repository
public interface FilterOptionRepository extends JpaRepository<FilterOption, Integer> {

    @Query("select fo from FilterOption fo join fetch fo.filterCategory")
    List<FilterOption> findAllFilterOptions();
}
