package ru.home.aglar.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.aspects.Timer;
import ru.home.aglar.market.dto.Cart;
import ru.home.aglar.market.dto.StringResponse;
import ru.home.aglar.market.services.CartService;

import java.security.Principal;

@Timer
@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{uuid}")
    public Cart getCart(Principal principal, @PathVariable String uuid) {
        return cartService.getCurrentCart(getCartKey(getUsername(principal), uuid));
    }

    @GetMapping("/generate")
    public StringResponse generateCartUuid() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/{uuid}/add/{id}")
    public void addProductToCart(Principal principal, @PathVariable String uuid, @PathVariable Long id) {
        cartService.addProductById(getCartKey(getUsername(principal), uuid), id);
    }

    @GetMapping("{uuid}/clear")
    public void clear(Principal principal, @PathVariable String uuid) {
        cartService.clear(getCartKey(getUsername(principal), uuid));
    }

    @GetMapping("/{uuid}/delete/{id}")
    public void deleteProductFromCart(Principal principal, @PathVariable String uuid, @PathVariable Long id) {
        cartService.deleteProductFromCart(getCartKey(getUsername(principal), uuid), id);
    }

    @GetMapping("/{uuid}/decrease/{id}")
    public void decreaseProductInCart(Principal principal, @PathVariable String uuid, @PathVariable Long id) {
        cartService.decreaseProductInCart(getCartKey(getUsername(principal), uuid), id);
    }

    @GetMapping("/{uuid}/merge")
    public void mergeCarts(Principal principal, @PathVariable String uuid) {
        String userCartKey = getCartKey(getUsername(principal), null);
        String guestCartKey = getCartKey(null, uuid);
        cartService.merge(userCartKey, guestCartKey);
    }

    private String getUsername(Principal principal) {
        return (principal == null) ? null : principal.getName();
    }

    private String getCartKey(String username, String uuid) {
        return cartService.generateCartFromSuffix(username == null ? uuid : username);
    }
}