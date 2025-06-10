package withdog.domain.place.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import withdog.aws.AwsS3Service;
import withdog.common.exception.CustomException;
import withdog.domain.place.dto.PlaceNewImageDto;
import withdog.domain.place.dto.PlaceUpdateImagesDto;
import withdog.domain.place.entity.Place;
import withdog.domain.place.entity.PlaceImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PlaceImageServiceImpl implements PlaceImageService {

    private final AwsS3Service awsS3Service;

    @Override
    public void save(Place place, List<PlaceNewImageDto> newImageDtos) {
        if (newImageDtos == null || newImageDtos.isEmpty()) {
            return;
        }

        List<String> successfullyUploadedS3Urls = new ArrayList<>();
        try {
            for (PlaceNewImageDto imageDto : newImageDtos) {

                String imageUrl = awsS3Service.upload(imageDto.getImage());
                successfullyUploadedS3Urls.add(imageUrl);

                PlaceImage placeImage = PlaceImage.builder()
                        .place(place)
                        .name(imageDto.getName())
                        .imagePosition(imageDto.getPosition())
                        .imageUrl(imageUrl)
                        .build();
                place.addImage(placeImage);
            }
        } catch (CustomException e) {
            //S3 '업로드 중' 예외 발생 시, 이미 성공적으로 업로드된 파일들이 있다면 롤백 시도
            cleanupS3Images(successfullyUploadedS3Urls);
            throw e; // 예외를 다시 던져 트랜잭션 롤백
        }

        refreshThumbnail(place); // 이미지 추가 후 썸네일 변경
        // S3 업로드 성공 후, 'DB 트랜잭션 롤백 시' 이미지 삭제 로직
        registerS3CleanupOnRollback(successfullyUploadedS3Urls, place.getName());
    }

    @Override
    public void update(Place place, List<PlaceUpdateImagesDto> updateImages) {
        if (updateImages == null || updateImages.isEmpty()) {
            return;
        }

        // 기존 for + stream (이중 루프) -> Map으로 개선
        Map<Long, PlaceImage> imagesMap = place.getPlaceImages().stream()
                .collect(Collectors.toMap(image -> image.getId(), image -> image));

        for (PlaceUpdateImagesDto updateImage : updateImages) {
            PlaceImage placeImage = imagesMap.get(updateImage.getImageId());

            if (placeImage == null) {
                log.warn("Image id {} does not exist in place {}. Skipping this id.", updateImage.getImageId(), place.getName());
                continue;
            }

            placeImage.updatePosition(updateImage.getImagePosition());
        }

        refreshThumbnail(place); // 이미지 업데이트 후 썸네일 변경
    }

    @Override
    public void delete(Place place, List<Long> removedImageIds) {
        if (removedImageIds == null || removedImageIds.isEmpty()) {
            return;
        }

        List<String> s3UrlsToDeleteOnCommit = new ArrayList<>();
        List<PlaceImage> imagesToRemoveFromPlaceEntity = new ArrayList<>();

        // 기존 for + stream (이중 루프) -> Map으로 개선
        Map<Long, PlaceImage> imagesMap = place.getPlaceImages().stream()
                .collect(Collectors.toMap(image -> image.getId(), image -> image));

        for (Long imageId : removedImageIds) {
            PlaceImage placeImage = imagesMap.get(imageId);
            if (placeImage != null) {
                imagesToRemoveFromPlaceEntity.add(placeImage); // ConcurrentModificationException 방지
                s3UrlsToDeleteOnCommit.add(placeImage.getImageUrl());
            } else {
                log.warn("Image id {} does not exist in place {}. Skipping this id.", imageId, place.getName());
            }
        }

        if (!imagesToRemoveFromPlaceEntity.isEmpty()) {
            place.getPlaceImages().removeAll(imagesToRemoveFromPlaceEntity);
        }

        refreshThumbnail(place); // 이미지 업데이트 후 썸네일 변경

        // DB 트랜잭션 '커밋 성공 시' 이미지 삭제 로직
        if (!s3UrlsToDeleteOnCommit.isEmpty()) {
            registerS3CleanupOnCommit(s3UrlsToDeleteOnCommit, place.getName());
        }
    }

    @Override
    public void deleteAll(List<Place> places) {
        if (places == null || places.isEmpty()) {
            return;
        }

        List<String> allS3UrlsToDeleteOnCommit = new ArrayList<>();
        for (Place place : places) {
            if (place.getPlaceImages() != null && !place.getPlaceImages().isEmpty()) {
                place.getPlaceImages().forEach(image -> {
                    if (image.getImageUrl() != null && !image.getImageUrl().isBlank()) {
                        allS3UrlsToDeleteOnCommit.add(image.getImageUrl());
                    }
                });
            }
        }

        if (!allS3UrlsToDeleteOnCommit.isEmpty()) {
            registerS3CleanupOnCommit(allS3UrlsToDeleteOnCommit, "selected places");
        }
    }

    //롤백시 이미지 삭제
    private void registerS3CleanupOnRollback(List<String> imageUrls, String placeName) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            return;
        }

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status == STATUS_ROLLED_BACK) {
                    log.warn("Transaction for place {} rolled back. Cleaning up {} S3 images.", placeName, imageUrls.size());
                    cleanupS3Images(imageUrls);
                }
            }
        });
    }

    //커밋시 이미지 삭제
    private void registerS3CleanupOnCommit(List<String> imageUrls, String placeName) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            return;
        }

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status == STATUS_COMMITTED) {
                    log.info("Transaction for place {} committed. Cleaning up {} S3 images.", placeName, imageUrls.size());
                    cleanupS3Images(imageUrls);
                }
            }
        });
    }

    private void cleanupS3Images(List<String> imageUrls) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            return;
        }

        for (String imageUrl : imageUrls) {
            if (imageUrl != null && !imageUrl.isBlank()) {
                awsS3Service.delete(imageUrl);
            }
        }
    }

    private void refreshThumbnail(Place place) {

        PlaceImage thumbnail = null;

        if (place.getPlaceImages() != null && !place.getPlaceImages().isEmpty()) {
            for (PlaceImage image : place.getPlaceImages()) {
                if (image.getImagePosition() == 0) {
                    thumbnail = image;
                    break;
                }
            }
        }
        place.updateThumbnail(thumbnail);
    }
}
