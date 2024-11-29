package withdog.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@Builder
@ToString
public class ResponseMemberInfoDto {

    private Long id;
    private String username;
    private String role;
}
