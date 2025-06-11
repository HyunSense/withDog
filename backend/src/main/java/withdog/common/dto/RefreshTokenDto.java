package withdog.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshTokenDto {

    private Long id;
    private String username;
    private String role;
    private String token;
    private long expired;

}
