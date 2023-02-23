package ru.home.aglar.front_service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FrontController {
    private final RestTemplate restTemplate;

    @GetMapping("/products/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return restTemplate.getForObject("http://product-service/api/v1/products/" + id, ProductDto.class);
    }

    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        return (List<ProductDto>) restTemplate.getForObject("http://product-service/api/v1/products", List.class);
    }
}