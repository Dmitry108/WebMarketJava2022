package ru.home.aglar.market.cart.backend.intergations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.home.aglar.market.core.api.ProductDto;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final WebClient productServiceWebClient;

    public ProductDto getProductById(Long id) {
        return productServiceWebClient.get()
                    .uri("/api/v1/products/" + id)
                    .retrieve()
                    .bodyToMono(ProductDto.class)
                    .block();
    }
}
