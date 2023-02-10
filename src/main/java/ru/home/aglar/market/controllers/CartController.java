package ru.home.aglar.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.dto.Cart;
import ru.home.aglar.market.services.CartService;
import ru.home.aglar.market.services.ProductService;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping
    public Cart getCart() {
        return cartService.getCart();
    }

    @GetMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id) {
        cartService.addProductById(id);
    }

    @GetMapping("/clear")
    public void clear() {
        cartService.clear();
    }

    @GetMapping("/delete/{id}")
    public void deleteProductFromCart(@PathVariable Long id) {
        cartService.deleteProductFromCart(id);
    }

    @GetMapping("/decrease/{id}")
    public void decreaseProductInCart(@PathVariable Long id) {
        cartService.decreaseProductInCart(id);
    }
}