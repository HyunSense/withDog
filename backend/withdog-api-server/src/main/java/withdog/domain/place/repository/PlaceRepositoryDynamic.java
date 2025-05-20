package withdog.domain.place.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import withdog.common.dto.response.SliceResponseDto;
import withdog.domain.place.dto.response.PlaceResponseDto;
import withdog.domain.place.entity.Place;

import java.util.List;


public interface PlaceRepositoryDynamic {

    SliceResponseDto<PlaceResponseDto> searchPlacesWithMultiFilters(String keyword, List<String> city, List<String> types,
                                                                    List<String> petAccessTypes, List<String> petSizes,
                                                                    List<String> services, Pageable pageable);

    Long getSearchPlacesTotalCount(String keyword, List<String> city, List<String> types,
                                   List<String> petAccessTypes, List<String> petSizes,
                                   List<String> services);
}
