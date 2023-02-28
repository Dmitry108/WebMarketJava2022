package ru.home.aglar.market.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CoreApp {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
