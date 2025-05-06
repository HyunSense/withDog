package withdog.domain.member.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequestDto {

//    @NotBlank
    private String username;

//    @NotBlank
    private String password;
}
