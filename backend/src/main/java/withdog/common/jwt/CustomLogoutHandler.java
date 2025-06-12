package withdog.common.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import withdog.common.constant.TokenType;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String REFRESH_TOKEN_KEY_PREFIX = "refreshToken:";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String clientRefreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(TokenType.REFRESH.name())) {
                    clientRefreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (clientRefreshToken == null) {
            log.warn("Logout request received, Client refresh token cookie is null.");
            return;
        }

        try {
            Long memberId = jwtTokenProvider.getId(clientRefreshToken);

            String redisKey = REFRESH_TOKEN_KEY_PREFIX + memberId;
            if (redisTemplate.hasKey(redisKey)) {
                redisTemplate.delete(redisKey);
                log.info("Successfully deleted refresh token for member: {}", memberId);
            } else {
                log.warn("Refresh token for member {} not found in Redis.", memberId);
            }

        } catch (JWTVerificationException e) {
            log.warn("Invalid refresh token during the logout process:{}", e.getMessage());
        }
    }
}
