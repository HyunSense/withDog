package withdog.domain.place.service;

import withdog.domain.place.dto.PlaceNewImageDto;
import withdog.domain.place.dto.PlaceUpdateImagesDto;
import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.PlaceImage;

import java.util.List;

public interface PlaceImageService {

    List<PlaceImage> findImages(Long placeId);
    void save(Place place, List<PlaceNewImageDto> newImages);
    void update(Place place, List<PlaceUpdateImagesDto> updateImages);
    void delete(Place place, List<Long> removeImageIds);
}
