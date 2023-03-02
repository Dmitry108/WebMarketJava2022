package ru.home.aglar.market.core.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.home.aglar.market.cart.api.CartDto;
import ru.home.aglar.market.common.exceptions.ResourceNotFoundException;
import ru.home.aglar.market.core.backend.entities.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.home.aglar.market.core.api.OrderDetailsDto;
import ru.home.aglar.market.core.backend.entities.OrderItem;
import ru.home.aglar.market.core.backend.entities.Product;
import ru.home.aglar.market.core.backend.repositories.OrderRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public void addNewOrder(String username, OrderDetailsDto orderDetailsDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("username", username);
        CartDto cartDto = restTemplate.exchange("http://localhost:8888/cart/api/v1/cart/{uuid}", HttpMethod.GET,
                new HttpEntity<>("", httpHeaders), CartDto.class, "null").getBody();
        Order order = new Order();
        order.setUsername(username);
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setTotalPrice(cartDto.getTotalPrice());
        List<OrderItem> orderItems = cartDto.getRecords().stream()
                .map(record -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    Product product = productService.getProductById(record.getProductId()).orElseThrow(() ->
                            new ResourceNotFoundException("Product not found"));
                    item.setProduct(product);
                    item.setQuantity(record.getQuantity());
                    item.setPricePerProduct(record.getPrice());
                    item.setPrice(record.getTotalPrice());
                    return item;
                }).toList();
        order.setOrderItems(orderItems);
        orderRepository.save(order);
        restTemplate.execute("http://localhost:8888/cart/api/v1/cart/{uuid}/clear", HttpMethod.GET, null,
                null, "null");
    }

    public List<Order> findOrdersByUsername(String username) {
        log.info(username);
        return orderRepository.findAllByUsername(username);
    }
}