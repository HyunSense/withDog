package withdog.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PlaceEditResponseDto {

    private String category;
    private Long id;
    private String name;
    private int price;
    private String addressPart1;
    private String addressPart2;
    private String phone;
    private String reservationLink;

}
