package withdog.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@Builder
@ToString
public class ResponseMemberInfoDto {

    private String username;
    private String role;
}
