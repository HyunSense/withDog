package withdog.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import withdog.entity.Member;
import withdog.repository.MemberRepository;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;
//    private static final int ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 30; // 30 min
    private static final int ACCESS_TOKEN_EXPIRED_TIME = 1000 * 5; // 5 s 테스트용
    private static final int REFRESH_TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 24 * 3; // 3 days

    public Long getId(String token) {

        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
                .getClaim("id")
                .asLong();
    }

    public String getUsername(String token) {

        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
                .getClaim("username")
                .asString();
    }
    public String getRole(String token) {

        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
                .getClaim("role")
                .asString();
    }

    public Boolean isExpired(String token) {

        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
                .getExpiresAt().before(new Date(System.currentTimeMillis()));

    }

    public String getExpired(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
                .getExpiresAt().toString();
    }

    public String createAccessToken(Long id, String username, String role) {

        return JWT.create()
                .withSubject("withDog")
                .withClaim("id", id)
                .withClaim("username", username)
                .withClaim("role", role)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED_TIME))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String createRefreshToken(Long id) {

        return JWT.create()
                .withSubject("withDog")
                .withClaim("id", id)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED_TIME))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public void tokenValidation(String token) {
        JWT.require(Algorithm.HMAC256(secretKey))
                .withSubject("withDog")
                .build()
                .verify(token);
    }

    public Member createMemberFromToken(String token) {

        return Member.builder()
                .id(getId(token))
                .username(getUsername(token))
                .role(getRole(token))
                .build();
    }
}
