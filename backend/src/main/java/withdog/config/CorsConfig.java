package withdog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        log.info("CorsConfig.corsFilter()");

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*"); // setAllowCredentials(true) 와 함께 사용불가, 사용하려면 addAllowedOriginPattern("*")
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
//        config.setExposedHeaders(Collections.singletonList("Authorization"));
        src.registerCorsConfiguration("/**", config);

        return new CorsFilter(src);
    }
}
