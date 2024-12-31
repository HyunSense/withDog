package withdog.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Slf4j
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        log.info("CorsConfig.corsFilter()");

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000"); // [*] 특수값이 포함되어있으면, setAllowCredentials(true) 와 함께 사용불가, 사용하려면 addAllowedOriginPattern("*")
        config.addAllowedOrigin("https://www.withdog.store");
//        config.addAllowedOriginPattern("http://localhost:3000");
//        config.addAllowedOriginPattern("https://d278iam8b79rj7.cloudfront.net");
//        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setExposedHeaders(List.of("Authorization"));
        src.registerCorsConfiguration("/**", config);

        return new CorsFilter(src);
    }
}
