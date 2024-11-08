package withdog.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import withdog.config.auth.CustomUserDetails;
import withdog.dto.response.DataResponseDto;
import withdog.dto.request.LoginRequestDto;
import withdog.dto.response.LoginResponseDto;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/login";
    private static final String HTTP_METHOD = "POST";

//    private static final Long EXPIRED_MS = 1000 * 60L * 10L;
//    private static final int ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 30; // 30 min
//    private static final int REFRESH_TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 24 * 3; // 3 days

    private final static AntPathRequestMatcher DEFAULT_LOGIN_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, ObjectMapper objectMapper) {
        super(DEFAULT_LOGIN_REQUEST_MATCHER);
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        LoginRequestDto loginRequestDto = objectMapper.readValue(request.getReader(), LoginRequestDto.class);
        log.info("loginRequestDto: {}", loginRequestDto);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successfulAuthentication");

        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        String username = customUserDetails.getUsername();
        Long id = customUserDetails.getId();
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority next = iterator.next();

        String role = next.getAuthority();

        String refreshToken = jwtTokenProvider.createRefreshToken(id);
        String accessToken = jwtTokenProvider.createAccessToken(id, username, role);
        String expired = jwtTokenProvider.getExpired(accessToken);

        LoginResponseDto dto = new LoginResponseDto(refreshToken, accessToken, expired);
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), DataResponseDto.success(dto));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        log.info("Authentication failed");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), "LOGIN FAILED");
    }
}
