package ru.home.aglar.market.cart.backend.intergations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.home.aglar.market.cart.backend.exceptions.CoreServiceIntegrationException;
import ru.home.aglar.market.common.exceptions.ResourceNotFoundException;
import ru.home.aglar.market.core.api.CoreServiceAppError;
import ru.home.aglar.market.core.api.ProductDto;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final WebClient productServiceWebClient;

    public ProductDto getProductById(Long id) {
        return productServiceWebClient.get()
                .uri("/api/v1/products/" + id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(CoreServiceAppError.class).map(body -> {
                            if (body.getCode().equals(CoreServiceAppError.CoreServiceErrors.RESOURCE_NOT_FOUND.name())) {
                                return new ResourceNotFoundException("Incorrect request to product-service: product not found");
                            }
                            return new CoreServiceIntegrationException("Incorrect request to product-service");
                        })
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                        Mono.error(new CoreServiceIntegrationException("Core-service is broken")))
                .bodyToMono(ProductDto.class)
                .block();
    }
}