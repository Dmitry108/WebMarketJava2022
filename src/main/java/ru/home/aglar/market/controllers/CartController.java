package ru.home.aglar.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.dto.CartRecordDto;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.exceptions.ResourceNotFoundException;
import ru.home.aglar.market.services.CartService;
import ru.home.aglar.market.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping
    public List<CartRecordDto> getCartRecords() {
        return cartService.getCartRecords();
    }

    @GetMapping("/{id}")
    public void addProductToCart(@PathVariable Long id) {
        Product product = productService.getProductById(id).orElseThrow(() -> new ResourceNotFoundException(null));
        cartService.addProductToCart(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductFromCart(@PathVariable Long id, @RequestParam(name = "d", required = false) Integer delta) {
        cartService.deleteProductFromCart(id, delta);
    }
}