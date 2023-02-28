package ru.home.aglar.market.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.home.aglar.market.cart.converters.CartConverter;
import ru.home.aglar.market.cart.dto.Cart;
import ru.home.aglar.market.cart.services.CartService;
import ru.home.aglar.market.common.dto.CartDto;
import ru.home.aglar.market.common.dto.ProductDto;
import ru.home.aglar.market.common.dto.StringResponse;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final RestTemplate restTemplate;
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/{uuid}")
    public CartDto getCart(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        return cartConverter.convertToDto(cartService.getCurrentCart(getCartKey(username, uuid)));
    }

    @GetMapping("/generate")
    public StringResponse generateCartUuid() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/{uuid}/add/{id}")
    public void addProductToCart(@RequestHeader(required = false) String username,
                                 @PathVariable String uuid, @PathVariable Long id) {
        ProductDto productdto = restTemplate.getForObject("http://localhost:8888/core/api/v1/products/{id}",
                ProductDto.class, id);
        cartService.addProduct(getCartKey(username, uuid), productdto);
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