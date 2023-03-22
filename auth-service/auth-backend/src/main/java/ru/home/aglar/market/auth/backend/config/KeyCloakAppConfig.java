package ru.home.aglar.market.auth.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Configuration
public class KeyCloakAppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .oauth2ResourceServer(configurer -> {
                    configurer.jwt(jwtConfigurer -> {
                        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
                        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
                            Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
                            Map<String, Object> postman = (java.util.Map<String, Object>) resourceAccess.get("postman");
                            List<String> roles = (List<String>) postman.get("roles");

                            return roles.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .map(it -> (GrantedAuthority) it)
                                    .toList();
                        });
                        jwtConfigurer.jwtAuthenticationConverter(converter);
                    });
                })
                .build();
    }
}
