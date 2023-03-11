package ru.home.aglar.market.cart.backend.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import ru.home.aglar.market.cart.backend.converters.CartConverter;
import ru.home.aglar.market.cart.backend.intergations.ProductServiceIntegration;
import ru.home.aglar.market.cart.backend.model.Cart;
import ru.home.aglar.market.cart.backend.model.CartRecord;
import ru.home.aglar.market.cart.backend.services.CartService;
import ru.home.aglar.market.core.api.ProductDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
    private static final String USER_CART = "myCart";
    private static final String GUEST_CART = "guestCart";
    private HttpHeaders httpHeaders;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CartConverter cartConverter;
    @MockBean
    private CartService cartService;
    @MockBean
    private ProductServiceIntegration productServiceIntegration;

    @BeforeEach
    public void init() {
        Mockito.doReturn(USER_CART).when(cartService).generateCartFromSuffix("Bob");
        Mockito.doReturn(GUEST_CART).when(cartService).generateCartFromSuffix("uuid");
        httpHeaders = new HttpHeaders();
        httpHeaders.add("username", "Bob");
    }

    @Test
    public void getCartTest() throws Exception {
        List<CartRecord> records = Arrays.asList(
                new CartRecord(1L, "Apple", 20, 2, 40),
                new CartRecord(2L, "Orange", 30, 3, 90));
        Cart cart = new Cart(new ArrayList<>(records), 140);

        given(cartService.getCurrentCart(USER_CART)).willReturn(cart);
        mockMvc.perform(get("/api/v1/cart/uuid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.records[0].title", is(cart.getRecords().get(0).getTitle())))
                .andExpect(jsonPath("$.totalPrice", is(cart.getTotalPrice())));

        List<CartRecord> guestRecords = List.of(
                new CartRecord(3L, "Mellon", 40, 2, 80));
        Cart guestCart = new Cart(new ArrayList<>(guestRecords), 80);
        given(cartService.getCurrentCart(GUEST_CART)).willReturn(guestCart);

        mockMvc.perform(get("/api/v1/cart/uuid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.records[0].title", is("Mellon")))//is(guestCart.getRecords().get(0).getTitle())))
                .andExpect(jsonPath("$.totalPrice", is(guestCart.getTotalPrice())));
    }

    @Test
    public void generateCartUuid() throws Exception {
        given(cartService.generateCartUuid()).willReturn("uuid");
        mockMvc.perform(get("/api/v1/cart/generate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", is("uuid")));
    }

    @Test
    public void addProductToCart() throws Exception {
        List<CartRecord> records = List.of(
                new CartRecord(2L, "Orange", 30, 3, 90));
        Cart cart = new Cart(new ArrayList<>(records), 90);
        ProductDto apple = new ProductDto(1L, "Apple", 20);

        given(cartService.getCurrentCart(USER_CART)).willReturn(cart);
        given(productServiceIntegration.getProductById(ArgumentMatchers.anyLong())).willReturn(apple);

        mockMvc.perform(get("/api/v1/cart/uuid/add/1")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(cartService, Mockito.times(1))
                .addProduct(ArgumentMatchers.anyString(), ArgumentMatchers.<ProductDto> any());
    }

    @Test
    public void clearDeleteDecreaseTest() throws Exception {
                mockMvc.perform(get("/api/v1/cart/uuid/decrease/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(cartService, Mockito.times(1))
                .decreaseProductInCart(USER_CART, 1L);

        mockMvc.perform(get("/api/v1/cart/uuid/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(cartService, Mockito.times(1))
                .deleteProductFromCart(USER_CART, 1L);

        mockMvc.perform(get("/api/v1/cart/uuid/clear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(cartService, Mockito.times(1))
                .clear(USER_CART);

        mockMvc.perform(get("/api/v1/cart/uuid/merge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(cartService, Mockito.times(1))
                .merge(USER_CART, GUEST_CART);
    }
}