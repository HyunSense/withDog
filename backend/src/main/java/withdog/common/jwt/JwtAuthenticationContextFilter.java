package withdog.common.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import withdog.common.constant.ApiResponseCode;
import withdog.common.constant.TokenType;
import withdog.common.config.auth.CustomUserDetails;
import withdog.common.dto.response.ResponseDto;
import withdog.domain.member.entity.Member;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationContextFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    private final List<AntPathRequestMatcher> whiteListMatchers = List.of(
            new AntPathRequestMatcher("/api/v1/login", "POST"),
            new AntPathRequestMatcher("/api/v1/members", "POST"),
            new AntPathRequestMatcher("/api/v1/refresh-token", "GET")
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return whiteListMatchers.stream()
                .anyMatch(matcher -> matcher.matches(request));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorization.substring("Bearer ".length());


        //TODO: 수정필요
        try {
            jwtTokenProvider.tokenValidation(accessToken, TokenType.ACCESS);

        } catch (JWTVerificationException e) {
            log.error(e.getMessage());

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            ResponseDto failure;
            if (e instanceof TokenExpiredException) {
                failure = ResponseDto.failure(ApiResponseCode.TOKEN_EXPIRED);

            } else {
                failure = ResponseDto.failure(ApiResponseCode.TOKEN_INVALID);
            }

            objectMapper.writeValue(response.getWriter(), failure);
            return;
        }

        Member member = jwtTokenProvider.createMemberFromToken(accessToken);

        CustomUserDetails customUserDetails = new CustomUserDetails(member);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        log.info("Successfully authenticated user '{}'. Security context updated.", customUserDetails.getUsername());
        filterChain.doFilter(request, response);
    }
}
