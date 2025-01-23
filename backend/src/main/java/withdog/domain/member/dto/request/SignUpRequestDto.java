package withdog.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import withdog.domain.member.entity.Member;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignUpRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @Builder
    public SignUpRequestDto(String username, String password, String name, String email) {

        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Member toEntity(String encodedPassword) {

        return Member.builder()
                .username(username)
                .password(encodedPassword)
                .name(name)
                .email(email)
                .role("ROLE_USER")
//                .createdAt(LocalDateTime.now())
                .build();
    }
}
