package withdog.aws;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

//@SpringBootTest
public class AwsConfigTest {

//    @Autowired
//    S3Client s3;

    @Test
    @DisplayName("IAM identity center(SSO LOGIN) TEST")
    void iamIdentityCenter() {
//        //given
//        S3Client s3Client = S3Client.builder()
//                .region(Region.AP_NORTHEAST_2)
//                .credentialsProvider(DefaultCredentialsProvider.builder().profileName("admin-profile").build())
//                .build();
//        //when
//        // S3 버킷 목록 조회
//        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
//        ListBucketsResponse listBucketsResponse = s3Client.listBuckets(listBucketsRequest);
//
//        //then
//        System.out.println("Bucket:");
//        listBucketsResponse.buckets().forEach(bucket -> System.out.println(bucket.name()));

    }

    @Test
    @DisplayName("s3 이미지 객체 url 가져오기")
    void getS3ObjectUrl() {
        //given
        String bucket = "withdog-portfolio";
        String fileName = "test";

        S3Client s3Client = S3Client.builder().region(Region.US_EAST_1).build();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();
        //when
//        GetUrlRequest request = GetUrlRequest.builder().bucket(bucket).key(fileName).build();
//        String publicUrl = s3Client.utilities().getUrl(request).toString();
        GetUrlRequest request1 = GetUrlRequest.builder().region(Region.AP_NORTHEAST_2).bucket(bucket).key(fileName).build();
        String publicUrl1 = s3Client.utilities().getUrl(request1).toString();
        //then
//        System.out.println("request.toString() = " + request.);
//        System.out.println("publicUrl = " + publicUrl);
        System.out.println("publicUrl1 = " + publicUrl1);

    }
}
