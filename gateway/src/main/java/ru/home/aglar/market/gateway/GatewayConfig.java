package ru.home.aglar.market.gateway;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@PropertySource("classpath:secrets.properties")
public class GatewayConfig {
}
