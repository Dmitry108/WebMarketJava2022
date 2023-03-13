package ru.home.aglar.market.cart.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.cart.api.CartDto;
import ru.home.aglar.market.cart.backend.converters.CartConverter;
import ru.home.aglar.market.cart.backend.exceptions.CartIsBrokenException;
import ru.home.aglar.market.cart.backend.intergations.ProductServiceIntegration;
import ru.home.aglar.market.cart.backend.services.CartService;
import ru.home.aglar.market.common.dto.StringResponse;
import ru.home.aglar.market.core.api.ProductDto;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final ProductServiceIntegration productServiceIntegration;
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
        ProductDto productdto = productServiceIntegration.getProductById(id);
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
    public void mergeCarts(@RequestHeader String username, @PathVariable String uuid) {
        String userCartKey = getCartKey(username, null);
        String guestCartKey = getCartKey(null, uuid);
        cartService.merge(userCartKey, guestCartKey);
    }

    private String getCartKey(String username, String uuid) {
        return cartService.generateCartFromSuffix(username == null ? uuid : username);
    }
}