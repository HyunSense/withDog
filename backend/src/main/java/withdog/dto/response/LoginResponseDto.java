package withdog.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Builder
public class LoginResponseDto {

    private Long id;
    private String username;
    private String role;
    private String refreshToken;
    private String accessToken;
    private String expired;


}
