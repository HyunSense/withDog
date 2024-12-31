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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import withdog.common.constant.ApiResponseCode;
import withdog.common.dto.response.ResponseDto;
import withdog.common.jwt.JwtAuthenticationFilter;
import withdog.common.jwt.JwtAuthorizationFilter;
import withdog.common.jwt.JwtTokenProvider;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
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
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/places/bookmarks",
                                "/api/v1/places/{id}/bookmarks",
                                "api/v1/places/{id}/bookmarks/status").authenticated()
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/v1/places/{id}/bookmarks",
                                "/api/v1/places/bookmarks").authenticated()
                        .anyRequest().permitAll())
                .addFilter(corsFilter)
                .addFilterAt(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtTokenProvider, objectMapper), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider, objectMapper), JwtAuthenticationFilter.class)
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
            response.setStatus(ApiResponseCode.AUTHORIZATION_FAILED.getStatus());
            response.setContentType("application/json");
            objectMapper.writeValue(response.getWriter(), ResponseDto.failure(ApiResponseCode.AUTHORIZATION_FAILED.getCode(), ApiResponseCode.AUTHORIZATION_FAILED.getMessage()));
        };
    }
}
