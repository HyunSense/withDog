package withdog.domain.place.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import withdog.aws.AwsS3Service;
import withdog.common.constant.ApiResponseCode;
import withdog.common.exception.CustomException;
import withdog.domain.place.dto.PlaceNewImageDto;
import withdog.domain.place.dto.PlaceUpdateImagesDto;
import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.PlaceImage;
import withdog.domain.place.repository.PlaceImageRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PlaceImageServiceImpl implements PlaceImageService {

    private final AwsS3Service awsS3Service;
    private final PlaceImageRepository placeImageRepository;

    @Override
    public List<PlaceImage> findImages(Long placeId) {

        return placeImageRepository.findByPlaceIdOrderByImagePositionAsc(placeId);
    }

    @Override
    public void save(Place place, List<PlaceNewImageDto> newImageDtos) {

        if (newImageDtos == null || newImageDtos.isEmpty()) {
            return;
        }

        List<String> imageUrlsForRollback = new ArrayList<>();

        for (PlaceNewImageDto imageDto : newImageDtos) {

            String imageUrl = awsS3Service.upload(imageDto.getImage());
            imageUrlsForRollback.add(imageUrl);

            PlaceImage placeImage = PlaceImage.builder()
                    .place(place)
                    .name(imageDto.getName())
                    .imagePosition(imageDto.getPosition())
                    .imageUrl(imageUrl)
                    .build();
            place.addImage(placeImage);
        }

        // 트랜잭션 롤백(후처리)
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status == STATUS_ROLLED_BACK) {
                    for (String imageUrlToDelete : imageUrlsForRollback) {
                        awsS3Service.delete(imageUrlToDelete);
                        log.info("Deleted S3 image: {}", imageUrlToDelete);
                    }
                } else if (status == STATUS_COMMITTED) {
                    log.info("Committed S3 images: {}", place.getName());
                }
            }
        });

        log.info("Place image saved to {}", place.getName());
    }

    //TODO: placeImage 수정후 Place의 Thumbnail_url 수정이 안됨
    //TODO: 리펙토링 필요 2중 for문
    @Override
    public void update(Place place, List<PlaceUpdateImagesDto> updateImages) {
        List<PlaceImage> placeImages = place.getPlaceImages();

        for (PlaceUpdateImagesDto updateImage : updateImages) {
            PlaceImage placeImage = placeImages.stream()
                    .filter(image -> image.getId().equals(updateImage.getImageId())).findFirst()
                    .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_IMAGE));

            placeImage.updatePosition(updateImage.getImagePosition());
            if (updateImage.getImagePosition() == 0) {
                place.updateThumbnail(placeImage);
            }
        }

        log.info("Place image updated to {}", place.getName());
    }

    @Override
    public void delete(Place place, List<Long> removeImageIds) {

        List<String> s3UrlsToDelete = new ArrayList<>();

        List<PlaceImage> placeImages = place.getPlaceImages();

        for (Long removeImageId : removeImageIds) {
            PlaceImage placeImage = placeImages.stream().filter(image -> image.getId().equals(removeImageId))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(ApiResponseCode.NOT_EXIST_IMAGE));

            place.getPlaceImages().remove(placeImage);
            s3UrlsToDelete.add(placeImage.getImageUrl());
        }


    }
}
