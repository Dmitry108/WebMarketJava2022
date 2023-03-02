package ru.home.aglar.market.cart.backend.services;

import lombok.RequiredArgsConstructor;
import ru.home.aglar.market.cart.backend.model.Cart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.home.aglar.market.core.api.ProductDto;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${utils.cart.prefix}")
    private String prefix;

    public String generateCartFromSuffix(String suffix) {
        return prefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        Cart cart = (Cart) redisTemplate.opsForValue().get(cartKey);
        return cart;
    }

    private void execute(String cartKey, Consumer<Cart> consumer) {
        Cart cart = getCurrentCart(cartKey);
        consumer.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void addProduct(String cartKey, ProductDto productDto) {
        execute(cartKey, cart -> cart.addProduct(productDto));
    }

    public void clear(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void deleteProductFromCart(String cartKey, Long id) {
        execute(cartKey, cart -> cart.deleteProduct(id));
    }

    public void decreaseProductInCart(String cartKey, Long id) {
        execute(cartKey, cart -> cart.decreaseProduct(id));
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void merge(String userCartKey, String guestCartKey) {
        Cart userCart = getCurrentCart(userCartKey);
        Cart guestCart = getCurrentCart(guestCartKey);
        userCart.merge(guestCart);
        updateCart(userCartKey, userCart);
        updateCart(guestCartKey, guestCart);
    }
}