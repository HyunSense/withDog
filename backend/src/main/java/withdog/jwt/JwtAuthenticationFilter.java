package withdog.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import withdog.common.ApiResponseCode;
import withdog.common.TokenType;
import withdog.config.auth.CustomUserDetails;
import withdog.dto.request.LoginRequestDto;
import withdog.dto.response.DataResponseDto;
import withdog.dto.response.LoginResponseDto;
import withdog.dto.response.ResponseDto;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
//    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/login";
    private static final String HTTP_METHOD = "POST";

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

        //TODO: 추후 커스텀 AuthenticationProvider 추가 고려
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        if (username == null || username.isBlank()) {
            throw new BadCredentialsException("Username is required");
        }
        if (password == null || password.isBlank()) {
            throw new BadCredentialsException("Password is required");
        }

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

        String accessToken = jwtTokenProvider.createToken(id, username, role, TokenType.ACCESS);
        String refreshToken = jwtTokenProvider.createToken(id, username, role, TokenType.REFRESH);
        String expired = jwtTokenProvider.getExpired(accessToken);

//        LoginResponseDto dto = new LoginResponseDto(refreshToken, accessToken, expired);
        LoginResponseDto dto = LoginResponseDto.builder()
                .id(id)
                .username(username)
                .role(role)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expired(expired)
                .build();

        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addCookie(createCookie(TokenType.REFRESH.name(), refreshToken));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), DataResponseDto.success(dto));
    }

    //TODO: 컴포넌트로 분리 필요
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 24 * 3); // 단위 s
        cookie.setHttpOnly(true);
//        cookie.setSecure(true); //TODO: 실제 배포, 도메인 연결시 CORS문제 체크
        cookie.setPath("/");
        return cookie;

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        log.info("Authentication failed");
        log.error("Authentication failed: {}", failed.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), ResponseDto.failure(ApiResponseCode.LOGIN_FAILED.getCode(), ApiResponseCode.LOGIN_FAILED.getMessage()));
    }
}
