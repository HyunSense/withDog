//package withdog.jwt;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import withdog.config.auth.CustomUserDetails;
//import withdog.domain.member.dto.response.LoginResponseDto;
//
//import java.io.IOException;
//import java.util.Collection;
//import java.util.Iterator;
//
//@Slf4j
//@RequiredArgsConstructor
//public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    private final JwtTokenProvider jwtTokenProvider;
//    private final ObjectMapper objectMapper;
//
//    private static final Long EXPIRED_MS = 1000 * 60L * 10L;
//
//    private static final String BEARER = "Bearer ";
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        log.info("Authentication success");
//
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        String username = customUserDetails.getUsername();
//        Long id = customUserDetails.getId();
//        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority next = iterator.next();
//
//        String role = next.getAuthority();
//
//        String accessToken = jwtTokenProvider.createAccessToken(id, username, role);
//        String refreshToken = jwtTokenProvider.createRefreshToken(id);
//        String expired = jwtTokenProvider.getExpired(accessToken);
//
//        LoginResponseDto dto = new LoginResponseDto(refreshToken, accessToken, expired);
//        response.addHeader("Authorization", BEARER + accessToken);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.setCharacterEncoding("UTF-8");
//
//        objectMapper.writeValue(response.getWriter(), dto);
//
//    }
//}
