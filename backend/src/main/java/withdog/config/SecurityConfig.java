package withdog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import withdog.dto.response.ResponseDto;
import withdog.jwt.JwtAuthenticationFilter;
import withdog.jwt.JwtAuthorizationFilter;
import withdog.jwt.JwtTokenProvider;
import withdog.repository.TokenRepository;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenProvider jwtTokenProvider;
//    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
//                .logout(logout -> logout.disable()) //TODO: disable 수정필요 로그인과 일관성있게
                .logout(logout -> logout
                        .logoutUrl("/logout")
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
                        .requestMatchers(HttpMethod.POST, "/places").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/places").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/places").hasRole("ADMIN")
                        .anyRequest().permitAll())
                .addFilter(corsFilter)
                .addFilterAt(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtTokenProvider, objectMapper), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider, objectMapper), JwtAuthenticationFilter.class);

        return http.build();
    }


}
