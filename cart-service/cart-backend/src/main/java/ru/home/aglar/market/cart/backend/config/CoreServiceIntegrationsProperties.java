package ru.home.aglar.market.cart.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "integrations.core-service")
public class CoreServiceIntegrationsProperties {
    private String url;
    private TimeoutsCoreServiceIntegrationsProperties timeouts;
}