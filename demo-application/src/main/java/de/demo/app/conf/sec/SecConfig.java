package de.demo.app.conf.sec;

import de.demo.app.sec.DemoAuthenticationProvider;
import de.demo.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception  {
        final String[] resources_protected = new String[] {"/**"};
        final String[] resources_unprotected = new String[] {"/v2/api-docs/**", "/v3/api-docs/**","/swagger-ui/**", "/swagger-ui.html" };
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) -> {
                            try {
                                authz
                                        .requestMatchers(resources_unprotected).permitAll()
                                        .requestMatchers(resources_protected)
                                        .authenticated()
                                        .and()
                                        .httpBasic();

                            } catch (Exception e) {
                                log.error("Error in SecurityConfiguration.filterChain()", e);
                            }
                        }
                );
        return http.build();
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider(@Autowired SecurityService securityService) {
        return new DemoAuthenticationProvider(securityService);
    }

}
