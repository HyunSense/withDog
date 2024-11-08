package withdog.dto.response;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final String refreshToken;
    private final String accessToken;
    private final String expired;

    public LoginResponseDto(String refreshToken, String accessToken, String expired) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.expired = expired;
    }
}
