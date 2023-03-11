package ru.home.aglar.market.core.backend.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.home.aglar.market.cart.api.CartDto;
import ru.home.aglar.market.cart.api.CartRecordDto;
import ru.home.aglar.market.core.api.OrderDetailsDto;
import ru.home.aglar.market.core.backend.entities.Order;
import ru.home.aglar.market.core.backend.entities.Product;
import ru.home.aglar.market.core.backend.integrations.CartServiceIntegration;
import ru.home.aglar.market.core.backend.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @MockBean
    private ProductService productService;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private CartServiceIntegration cartServiceIntegration;

    @Test
    public void addNewOrderTest() {
        OrderDetailsDto orderDetailsDto = new OrderDetailsDto("111-11-11", "Moscow");
        List<CartRecordDto> recordDtoList = List.of(
                new CartRecordDto(1L, "Apple", 10, 2, 20),
                new CartRecordDto(2L, "Orange", 20, 3, 60));
        CartDto cartDto = new CartDto(new ArrayList<>(recordDtoList), 80);

        Mockito.doReturn(cartDto).when(cartServiceIntegration).getUserCart(ArgumentMatchers.anyString());
        Mockito.doNothing().when(cartServiceIntegration).clearUserCart(ArgumentMatchers.anyString());

        Optional<Product> apple = Optional.of(new Product(1L, "Apple", 10));
        Optional<Product> orange = Optional.of(new Product(2L, "Orange", 20));

        Mockito.doReturn(apple).when(productService).getProductById(1L);
        Mockito.doReturn(orange).when(productService).getProductById(2L);

        orderService.addNewOrder("username", orderDetailsDto);
        Mockito.verify(orderRepository, Mockito.times(1)).save(
                ArgumentMatchers.isA(Order.class));
//        Assertions.assertEquals("Bob", order.getUsername());
//        Assertions.assertEquals("Apple", order.getOrderItems().get(0).getProduct().getTitle());
//        Assertions.assertEquals("Orange", order.getOrderItems().get(1).getProduct().getTitle());
//        Assertions.assertEquals(orderDetailsDto.getAddress(), order.getAddress());
//        Assertions.assertEquals(orderDetailsDto.getAddress(), order.getPhone());
    }

    @Test
    public void findOrdersByUsername() {
        String username = "Bob";
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Order order = new Order();
            order.setId((long) i);
            orders.add(order);
        }
        Mockito.doReturn(orders).when(orderRepository).findAllByUsername(username);
        Assertions.assertEquals(orders, orderService.findOrdersByUsername(username));
    }
}