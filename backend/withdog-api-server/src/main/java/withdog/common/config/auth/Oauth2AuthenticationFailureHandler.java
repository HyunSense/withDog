package withdog.common.config.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.error("OAuth2 Authentication failed: {}", exception.getMessage());

//        String domain = "http://localhost:3000";
        String domain = "https://www.withdog.store";

        String redirectUrl = UriComponentsBuilder
                .fromHttpUrl(domain)
                .path("/oauth2/redirect")
                .queryParam("error", "authentication_failed")
                .queryParam("message", exception.getMessage())
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }

}
