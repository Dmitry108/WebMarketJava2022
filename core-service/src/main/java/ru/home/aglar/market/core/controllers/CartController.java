package ru.home.aglar.market.core.controllers;

import lombok.RequiredArgsConstructor;
import ru.home.aglar.market.common.dto.StringResponse;
import ru.home.aglar.market.core.aspects.Timer;
import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.core.dto.Cart;
import ru.home.aglar.market.core.services.CartService;

//@Timer
@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
//@CrossOrigin("*")
public class CartController {
    private final CartService cartService;

    @GetMapping("/{uuid}")
    public Cart getCart(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        return cartService.getCurrentCart(getCartKey(username, uuid));
    }

    @GetMapping("/generate")
    public StringResponse generateCartUuid() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/{uuid}/add/{id}")
    public void addProductToCart(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        cartService.addProductById(getCartKey(username, uuid), id);
    }

    @GetMapping("{uuid}/clear")
    public void clear(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.clear(getCartKey(username, uuid));
    }

    @GetMapping("/{uuid}/delete/{id}")
    public void deleteProductFromCart(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        cartService.deleteProductFromCart(getCartKey(username, uuid), id);
    }

    @GetMapping("/{uuid}/decrease/{id}")
    public void decreaseProductInCart(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        cartService.decreaseProductInCart(getCartKey(username, uuid), id);
    }

    @GetMapping("/{uuid}/merge")
    public void mergeCarts(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        String userCartKey = getCartKey(username, null);
        String guestCartKey = getCartKey(null, uuid);
        cartService.merge(userCartKey, guestCartKey);
    }

    private String getCartKey(String username, String uuid) {
        return cartService.generateCartFromSuffix(username == null ? uuid : username);
    }
}