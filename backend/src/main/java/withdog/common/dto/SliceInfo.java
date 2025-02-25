package withdog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SliceInfo {

    private int page;
    private int size;
    private boolean first;
    private boolean last;
    private boolean hasNext;
}
