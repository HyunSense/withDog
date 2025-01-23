package withdog.common.config.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import withdog.common.constant.TokenType;
import withdog.common.jwt.JwtTokenProvider;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = customUserDetails.getUsername();
        Long id = customUserDetails.getId();
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority next = iterator.next();
        String role = next.getAuthority();

        String accessToken = jwtTokenProvider.createToken(id, username, role, TokenType.ACCESS);
        String refreshToken = jwtTokenProvider.createToken(id, username, role, TokenType.REFRESH);
        log.info("Oauth2 Success accessToken: {}", accessToken);
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addCookie(createCookie(TokenType.REFRESH.name(), refreshToken));

        String domain = "http://localhost:3000";
        String redirectUrl = UriComponentsBuilder
                .fromHttpUrl(domain)
                .path("login-success")
                .queryParam("accessToken", accessToken)
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }

    //TODO: 컴포넌트로 분리 필요
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 24 * 3); // 단위 s
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }
}
