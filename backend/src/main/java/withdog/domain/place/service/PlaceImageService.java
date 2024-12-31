package withdog.domain.place.service;

import withdog.domain.place.dto.PlaceNewImageDto;
import withdog.domain.place.dto.PlaceUpdateImagesDto;
import withdog.domain.place.entity.Place;

import java.io.IOException;
import java.util.List;

public interface PlaceImageService {

//    void save(Place place, List<PlaceNewImageDto> newImages);
//    void update(Place place, List<PlaceUpdateImagesDto> updateImages);
//    void delete(Place place, List<Long> removeImageIds);

    void save(Place place, List<PlaceNewImageDto> newImages);
    void update(Place place, List<PlaceUpdateImagesDto> updateImages);
    void delete(Place place, List<Long> removeImageIds);
}
