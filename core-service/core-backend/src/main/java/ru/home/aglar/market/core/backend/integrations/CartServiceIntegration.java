package ru.home.aglar.market.core.backend.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.home.aglar.market.cart.api.CartDto;
import ru.home.aglar.market.cart.api.CartServiceAppError;
import ru.home.aglar.market.core.backend.exceptions.CartServiceIntegrationException;

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
                .uri("/api/v1/cart/0")
                .header("username", username)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(CartServiceAppError.class).map(
                        body -> {
                            System.out.println(body.getClass().getTypeName() +" "+
                                    body + " " + body.getCode() + " " + body.getMessage());
                            if (body.getCode().equals(CartServiceAppError.CartServiceErrors.CART_NOT_FOUND.name())) {
                                return new CartServiceIntegrationException("Incorrect request to cart-service: cart not found");
                            }
                            return new CartServiceIntegrationException("Incorrect request to cart-service");
                        }))
                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                        Mono.error(new CartServiceIntegrationException("Cart-service is broken")))
                .bodyToMono(CartDto.class)
                .block();
    }
}