package ru.home.aglar.market.core.backend.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.home.aglar.market.cart.api.CartDto;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public void clearUserCart(String username) {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/null/clear")
                .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public CartDto getUserCart(String username) {
        return cartServiceWebClient.get()
                .uri("/api/v1/cart/null")
                .header("username", username)
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
    }
}
