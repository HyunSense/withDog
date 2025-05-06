package withdog.aws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
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
public class AwsFileService {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.cloudfront.base-url}")
    private String cloudFrontBaseUrl;

    private final static String PLACE_IMG_DIR = "images/";

    //TODO IO EXCEPTION 처리 필요
    public String upload(MultipartFile image) {
        log.info("awsFileService uploading");
//        String fileName = PLACE_IMG_DIR + UUID.randomUUID() + "_" + image.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

        try {
        InputStream inputStream = image.getInputStream();
        // buckey policy(json) 정책을 사용했으므로 ACL 지정 X
        // 클라이언트 admin으로부터 업로드 할 수 있는 보안정책이 추가적으로 필요
        // 현재 클라이언트 JWT 인증토큰을 이용하여 Controller에서만 체크후 uploadService 이용가능
        // 보안에 취약하다?
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(PLACE_IMG_DIR + fileName)
                .contentType(image.getContentType())
                .contentLength(image.getSize())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, image.getSize()));
        } catch (Exception e) {
            throw new CustomException(ApiResponseCode.SERVER_ERROR, e.getMessage());
        }

//        GetUrlRequest request = GetUrlRequest.builder().bucket(bucket).key(fileName).build();
//        return s3Client.utilities().getUrl(request).toString();
        String imageUrl = cloudFrontBaseUrl + fileName;
        log.info("imageUrl = {}", imageUrl);

        return imageUrl;
    }

}
