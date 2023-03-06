package ru.home.aglar.market.cart.backend.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import ru.home.aglar.market.cart.backend.model.Cart;
import ru.home.aglar.market.cart.backend.model.CartRecord;
import ru.home.aglar.market.core.api.ProductDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = CartService.class)
public class CartServiceTest {
    @Autowired
    private CartService cartService;

    @MockBean
    private RedisTemplate redisTemplate;

    @MockBean
    private ValueOperations valueOperations;

    private Cart cart;

    @BeforeEach
    public void initCart() {
        List<CartRecord> records = Arrays.asList(
                new CartRecord(1L, "Apple", 10, 2, 20),
                new CartRecord(2L, "Orange", 20, 3, 60));
        cart = new Cart(new ArrayList<>(records), 80);
    }

    @Test
    public void generateCartNameTest() {
        Assertions.assertEquals("marLigurosSuffix", cartService.generateCartFromSuffix("Suffix"));
    }

    @Test
    public void getCurrentCart() {
        String existsCart = "myCart";
        String notExistsCart = "newCart";
        Cart newCart = new Cart();
        Mockito.doReturn(true).when(redisTemplate).hasKey(existsCart);
        Mockito.doReturn(false).when(redisTemplate).hasKey(notExistsCart);
        Mockito.doReturn(valueOperations).when(redisTemplate).opsForValue();
        Mockito.doReturn(cart).when(valueOperations).get(existsCart);

        Assertions.assertEquals(cart, cartService.getCurrentCart(existsCart));
        Mockito.verify(valueOperations, Mockito.times(0)).set(existsCart, cart);

        cartService.getCurrentCart(notExistsCart);
        Mockito.verify(valueOperations, Mockito.times(1)).set(notExistsCart, newCart);
    }

    @Test
    public void executeCartFunctions() {
        String myCartKey = "myCart";
        ProductDto apple = new ProductDto(3L, "Mellon", 30);
        Mockito.doReturn(valueOperations).when(redisTemplate).opsForValue();
        Mockito.doReturn(cart).when(valueOperations).get(myCartKey);
        cartService.addProduct(myCartKey, apple);
        cartService.addProduct(myCartKey, apple);
        Assertions.assertEquals(3, cart.getRecords().size());
        Assertions.assertEquals(140, cart.getTotalPrice());
        Mockito.verify(valueOperations, Mockito.times(2)).set(myCartKey, cart);
        cartService.decreaseProductInCart(myCartKey, 2L);
        Assertions.assertEquals(3, cart.getRecords().size());
        Assertions.assertEquals(120, cart.getTotalPrice());
        Mockito.verify(valueOperations, Mockito.times(3)).set(myCartKey, cart);
        cartService.deleteProductFromCart(myCartKey, 3L);
        Assertions.assertEquals(2, cart.getRecords().size());
        Assertions.assertEquals(60, cart.getTotalPrice());
        Mockito.verify(valueOperations, Mockito.times(4)).set(myCartKey, cart);
        cartService.clear(myCartKey);
        Assertions.assertEquals(0, cart.getRecords().size());
        Assertions.assertEquals(0, cart.getTotalPrice());
        Mockito.verify(valueOperations, Mockito.times(5)).set(myCartKey, cart);
    }

    @Test
    public void mergeCartsTest() {
        String myCartKey = "myCart";
        String anotherCartKey = "anotherCart";
        List<CartRecord> records = Arrays.asList(
                new CartRecord(1L, "Apple", 10, 1, 10),
                new CartRecord(3L, "Mellon", 30, 2, 60));
        Cart anotherCart = new Cart(new ArrayList<>(records), 70);
        Mockito.doReturn(valueOperations).when(redisTemplate).opsForValue();
        Mockito.doReturn(cart).when(valueOperations).get(myCartKey);
        Mockito.doReturn(anotherCart).when(valueOperations).get(anotherCartKey);
        cartService.merge(myCartKey, anotherCartKey);
        Assertions.assertEquals(3, cart.getRecords().size());
        Assertions.assertEquals(150, cart.getTotalPrice());
        Assertions.assertEquals(0, anotherCart.getRecords().size());
        Assertions.assertEquals(0, anotherCart.getTotalPrice());
    }
}