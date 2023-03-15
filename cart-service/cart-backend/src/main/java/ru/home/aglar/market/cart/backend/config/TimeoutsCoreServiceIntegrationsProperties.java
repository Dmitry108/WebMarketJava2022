package ru.home.aglar.market.cart.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "integrations.core-service.timeouts")
public class TimeoutsCoreServiceIntegrationsProperties {
    private Integer connection;
    private Integer reading;
    private Integer writing;
}
