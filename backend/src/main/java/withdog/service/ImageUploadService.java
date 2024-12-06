package withdog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import withdog.dto.PlaceImageUploadDto;
import withdog.dto.response.DataResponseDto;
import withdog.dto.response.ResponseDto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//TODO: Transaction 처리?
@Slf4j
@Service
public class ImageUploadService {

    private static final String UPLOAD_DIR = System.getProperty("user.dir")+"/uploads";

    public List<PlaceImageUploadDto> saveLocal(List<MultipartFile> images) {

//        List<String> imageUrls = new ArrayList<>();
        List<PlaceImageUploadDto> uploadDtos = new ArrayList<>();
        int position = 1;
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                // file:///D:/...
                // file:// => [file]: scheme, [:]: 구분자, [//]: authority(리소스를 제공하는 서버 또는 호스트, authority가 있다는것을 의미함)
                // file:/// 의 경우 file:// 후 authority가 비어있기때문에 세번째 /는 경로가 시작된다는 뜻, /D:...
                // authority가 비어있는경우 // 생략가능 => file:/// 를 file:/ 로
                try {// 파일이나 디렉토리를 통한 객체 생성

                    File uploadDir = new File(UPLOAD_DIR);
                    if (!uploadDir.exists()) { // 파일이나 디렉토리가 존재하는지 확인
                        uploadDir.mkdirs(); // 새로운 디렉토리 생성
                    }

                    String originalFilename = image.getOriginalFilename();
                    String uniqueFilename = UUID.randomUUID() + "_" + originalFilename;
                    // new File(경로, 파일)
                    File destinationFile = new File(UPLOAD_DIR, uniqueFilename);
                    // MultipartFile.transferTo(저장 경로): 파일의 저장
                    image.transferTo(destinationFile);
                    log.info("File saved at: {}", destinationFile.getAbsolutePath());

//                    imageUrls.add(destinationFile.toURI().toString());
                    String imageUrl = "/uploads/" + uniqueFilename;
//                    imageUrls.add(imageUrl);
                    log.info("image.getName = {}", image.getName());
                    PlaceImageUploadDto dto = PlaceImageUploadDto.builder()
                            .name(image.getOriginalFilename())
                            .imagePosition(position++)
                            .imageUrl(imageUrl).build();
                    uploadDtos.add(dto);
                } catch (IOException e) {
                    log.error("Error saving File: {}", image.getOriginalFilename(), e);
//                    return 필요?,  throw CustomException 생성?
                }
            }
        }

        return uploadDtos;
    }

}
