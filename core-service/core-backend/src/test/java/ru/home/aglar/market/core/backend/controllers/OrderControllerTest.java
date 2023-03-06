package ru.home.aglar.market.core.backend.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.home.aglar.market.core.api.OrderDetailsDto;
import ru.home.aglar.market.core.api.OrderDto;
import ru.home.aglar.market.core.api.OrderItemDto;
import ru.home.aglar.market.core.backend.converters.OrderConverter;
import ru.home.aglar.market.core.backend.entities.Order;
import ru.home.aglar.market.core.backend.entities.OrderItem;
import ru.home.aglar.market.core.backend.services.OrderService;

import java.net.URI;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OrderControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private OrderConverter orderConverter;
    @MockBean
    private OrderService orderService;

    private HttpHeaders header;

    @BeforeEach
    public void initHeader() {
        header = new HttpHeaders();
        header.add("username", "Bob");
    }

    @Test
    public void doOrderTest() {
        OrderDetailsDto orderDetailsDto = new OrderDetailsDto("111-11-11", "Moscow");
        RequestEntity<OrderDetailsDto> request = new RequestEntity<>(
                orderDetailsDto,
                header,
                HttpMethod.POST,
                URI.create("/api/v1/orders"));
        ResponseEntity<?> response = restTemplate.exchange(request, Object.class);

        Mockito.doNothing().when(orderService).addNewOrder(ArgumentMatchers.anyString(), ArgumentMatchers.any());

        Mockito.verify(orderService, Mockito.times(1)).addNewOrder(
                ArgumentMatchers.eq("Bob"), ArgumentMatchers.isA(OrderDetailsDto.class));
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void getOrderTest() {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Order order = new Order();
            order.setId((long) i);
            orders.add(order);
        }
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OrderDto orderDto = new OrderDto();
            orderDto.setId((long) i);
            orderDtoList.add(orderDto);
        }
        Mockito.when(orderService.findOrdersByUsername(ArgumentMatchers.anyString())).thenReturn(orders);
        Mockito.when(orderConverter.convertToDto(orders.get(0))).thenReturn(orderDtoList.get(0));
        Mockito.when(orderConverter.convertToDto(orders.get(1))).thenReturn(orderDtoList.get(1));
        Mockito.when(orderConverter.convertToDto(orders.get(2))).thenReturn(orderDtoList.get(2));

        RequestEntity<?> request = new RequestEntity<>(header, HttpMethod.GET, URI.create("/api/v1/orders"));
        ResponseEntity<List> response = restTemplate.exchange(request, List.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}