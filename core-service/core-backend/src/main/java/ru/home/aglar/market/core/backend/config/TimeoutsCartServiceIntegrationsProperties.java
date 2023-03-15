package ru.home.aglar.market.core.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "integrations.cart-service.timeouts")
public class TimeoutsCartServiceIntegrationsProperties {
    private Integer connection;
    private Integer reading;
    private Integer writing;
}