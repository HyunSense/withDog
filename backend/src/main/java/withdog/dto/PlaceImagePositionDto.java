package withdog.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //데이터 바인딩시 중첩(depth)가 깊어지는 경우 기본생성자와 Setter가 필요하다?
@ToString
public class PlaceImagePositionDto {

    private int id;
    private int position;

}
