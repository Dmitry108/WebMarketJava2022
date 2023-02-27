package ru.home.aglar.market.core.services;

import lombok.RequiredArgsConstructor;
import ru.home.aglar.market.common.exceptions.ResourceNotFoundException;
import ru.home.aglar.market.core.dto.Cart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.home.aglar.market.core.entities.Product;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
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
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    private void execute(String cartKey, Consumer<Cart> consumer) {
        Cart cart = getCurrentCart(cartKey);
        consumer.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void addProductById(String cartKey, Long id) {
        execute(cartKey, cart -> {
            if (cart.increaseIfExists(id)) return;
            Product product = productService.getProductById(id).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Product with id = %d doesn't found", id)));
            cart.addProduct(product);
        });
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