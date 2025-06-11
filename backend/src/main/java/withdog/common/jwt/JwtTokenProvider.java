package withdog.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import withdog.common.constant.TokenType;
import withdog.domain.member.entity.Member;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

//    @Value("${spring.jwt.secret}")
    @Value("${spring.bearer.jwt}")
    private String secretKey;

    public Long getIdFromExpiredToken(String expiredToken) {
        try {
            DecodedJWT decodedJWT = JWT.decode(expiredToken);
            return decodedJWT.getClaim("id").asLong();
        } catch (Exception e) {
            log.error("getIdFromExpiredToken error : {}", e.getMessage());
            return null;
        }
    }

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

    public long getExpired(String token) {

        long expired = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
                .getExpiresAt().getTime();

        return (expired - System.currentTimeMillis()) / 1000;
    }

    public String getTokenType(String token) {

        return JWT.require(Algorithm.HMAC256(secretKey))
//                .withClaim("tokenType",tokenType.name())
                .build()
                .verify(token)
                .getClaim("tokenType")
                .asString();
    }

    @Builder
    public String createToken(Long memberId, String username, String role, TokenType tokenType) {

        return JWT.create()
                .withSubject("withDog")
                .withClaim("tokenType", tokenType.name())
                .withClaim("id", memberId)
                .withClaim("username", username)
                .withClaim("role", role)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenType.getExpiration()))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public void tokenValidation(String token, TokenType tokenType) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .withSubject("withDog")
                .build()
                .verify(token);

        if (decodedJWT.getClaim("id").isMissing() ||
            decodedJWT.getClaim("username").isMissing()||
            decodedJWT.getClaim("role").isMissing() ||
            decodedJWT.getClaim("tokenType").isMissing()) {

            throw new JWTVerificationException("Token is missing required claims");
        }
    }

    public Member createMemberFromToken(String token) {

        return Member.builder()
                .id(getId(token))
                .username(getUsername(token))
                .role(getRole(token))
                .build();
    }
}
