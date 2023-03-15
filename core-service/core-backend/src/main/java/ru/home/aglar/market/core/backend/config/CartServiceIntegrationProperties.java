package ru.home.aglar.market.core.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
//@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.cart-service")
public class CartServiceIntegrationProperties {
    private String url;
    private TimeoutsCartServiceIntegrationsProperties timeouts;
}