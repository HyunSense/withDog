package withdog.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import withdog.common.config.auth.CustomOauth2UserService;
import withdog.common.config.auth.Oauth2AuthenticationFailureHandler;
import withdog.common.config.auth.Oauth2AuthenticationSuccessHandler;
import withdog.common.constant.ApiResponseCode;
import withdog.common.dto.response.ResponseDto;
import withdog.common.filter.SessionIdFilter;
import withdog.common.filter.jwt.JwtLoginProcessingFilter;
import withdog.common.filter.jwt.JwtAuthenticationContextFilter;
import withdog.common.filter.jwt.JwtTokenProvider;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    //TODO: Filter 의존성 문제 해결 필요 new 연산자 X
    private final CorsFilter corsFilter;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOauth2UserService customOauth2UserService;
    private final Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
    private final Oauth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;
    private final ObjectMapper objectMapper;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userinfo -> userinfo.userService(customOauth2UserService)) // 생략가능
                        .successHandler(oauth2AuthenticationSuccessHandler)
                        .failureHandler(oauth2AuthenticationFailureHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/api/v1/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(200);
                            response.setContentType("application/json");
                            objectMapper.writeValue(response.getWriter(), ResponseDto.success());
                        })
                        .deleteCookies("REFRESH"))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .authorizeHttpRequests(req -> req
                        .requestMatchers(HttpMethod.POST, "/api/v1/places").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/places").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/places").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/places/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/places/bookmarks",
                                "/api/v1/places/{id}/bookmarks/status").authenticated()
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/places/{id}/bookmarks").authenticated()
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/v1/places/{id}/bookmarks",
                                "/api/v1/places/bookmarks").authenticated()
                        .anyRequest().permitAll())
                .addFilter(corsFilter)
                .addFilterAt(new JwtLoginProcessingFilter(authenticationManager(authenticationConfiguration), jwtTokenProvider, objectMapper), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationContextFilter(jwtTokenProvider, objectMapper), AuthorizationFilter.class)
                .addFilterBefore(new SessionIdFilter(), JwtLoginProcessingFilter.class)
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint(unAuthorizedEntryPoint())
                );

        return http.build();
    }

    // 인증 O, 권한 X
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {

        return (request, response, accessDeniedException) -> {
            response.setStatus(ApiResponseCode.ACCESS_DENIED.getStatus());
            response.setContentType("application/json");
            objectMapper.writeValue(response.getWriter(), ResponseDto.failure(ApiResponseCode.ACCESS_DENIED.getCode(), ApiResponseCode.ACCESS_DENIED.getMessage()));
        };
    }

    // 인증 X, ex: 북마크 조회,삭제 )
    @Bean
    public AuthenticationEntryPoint unAuthorizedEntryPoint() {

        return (request, response, authException) -> {
            log.info("unAuthorizedEntryPoint called for URI: {}, Exception: {}", request.getRequestURI(), authException.getMessage());
            response.setStatus(ApiResponseCode.AUTHORIZATION_FAILED.getStatus());
            response.setContentType("application/json");
            objectMapper.writeValue(response.getWriter(), ResponseDto.failure(ApiResponseCode.AUTHORIZATION_FAILED.getCode(), ApiResponseCode.AUTHORIZATION_FAILED.getMessage()));
        };
    }
}
