package withdog.common.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import withdog.common.config.auth.CustomUserDetails;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String REFRESH_TOKEN_KEY_PREFIX = "refreshToken:";

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        log.info("Execute CustomLogoutHandler logout method.");

        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            log.info("authentication is null or not CustomUserDetails");
            return;
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long memberId = customUserDetails.getId();
        String redisKey = REFRESH_TOKEN_KEY_PREFIX + memberId;

        if (redisTemplate.hasKey(redisKey)) {
            redisTemplate.delete(redisKey);
            log.info("Successfully deleted refresh token for member: {}", memberId);
        } else {
            log.warn("Refresh token for member {} not found in Redis.", memberId);
        }
    }
}
