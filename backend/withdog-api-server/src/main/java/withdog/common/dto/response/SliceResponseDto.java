package withdog.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import withdog.common.dto.SliceInfo;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SliceResponseDto<T> {

    private SliceInfo sliceInfo;
    private List<T> content;

}
