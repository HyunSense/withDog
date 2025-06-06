package withdog.aws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import withdog.common.constant.ApiResponseCode;
import withdog.common.exception.CustomException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AwsS3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.cloudfront.base-url}")
    private String cloudFrontBaseUrl;

    private final static String PLACE_IMG_DIR = "images/";

    public String upload(MultipartFile image) {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

        try {
            InputStream inputStream = image.getInputStream();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(PLACE_IMG_DIR + fileName)
                    .contentType(image.getContentType())
                    .contentLength(image.getSize())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, image.getSize()));

        } catch (IOException e) {
            log.error("FileStream processing error", e);
            throw new CustomException(ApiResponseCode.SERVER_ERROR, "이미지 파일 처리 중 오류 발생");

        } catch (Exception e) {
            log.error("S3 upload error", e);
            throw new CustomException(ApiResponseCode.SERVER_ERROR, "S3 업로드 중 오류 발생");
        }

        String imageUrl = cloudFrontBaseUrl + fileName;

        return imageUrl;
    }

    public void delete(String imageUrl) {

        if (imageUrl == null || !imageUrl.startsWith(cloudFrontBaseUrl)) {
            log.info("Invalid or non-CloudFront URL: {}", imageUrl);
            return;
        }

        String fileName = imageUrl.substring(cloudFrontBaseUrl.length());
        String s3Key = PLACE_IMG_DIR + fileName;

        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(s3Key)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);

        } catch (Exception e) {
            log.error("Failed to delete file from S3: key: {}, Error: {}", s3Key, e.getMessage(), e);
        }

    }

}
