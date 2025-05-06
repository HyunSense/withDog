package withdog.domain.place.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PlaceNewImageDto {

    private int position;
    private String name;
    private MultipartFile image;
}
