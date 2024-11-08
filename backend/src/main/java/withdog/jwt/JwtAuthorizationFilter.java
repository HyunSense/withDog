package withdog.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import withdog.config.auth.CustomUserDetails;
import withdog.entity.Member;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring("Bearer ".length());
        log.info("token = {}", token);
        log.info("token getExpired = {}", jwtTokenProvider.getExpired(token));
        try {
            //TODO: 수정필요
            jwtTokenProvider.isExpired(token);

        } catch (JWTVerificationException e) {

            request.setAttribute("exception", e);
            filterChain.doFilter(request, response);
            return;
        }

        Member member = jwtTokenProvider.createMemberFromToken(token);

        CustomUserDetails customUserDetails = new CustomUserDetails(member);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
