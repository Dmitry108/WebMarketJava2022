package ru.home.aglar.market.core.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.home.aglar.market.cart.api.CartDto;
import ru.home.aglar.market.common.exceptions.ResourceNotFoundException;
import ru.home.aglar.market.core.backend.entities.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.home.aglar.market.core.api.OrderDetailsDto;
import ru.home.aglar.market.core.backend.entities.OrderItem;
import ru.home.aglar.market.core.backend.entities.Product;
import ru.home.aglar.market.core.backend.integrations.CartServiceIntegration;
import ru.home.aglar.market.core.backend.repositories.OrderRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final CartServiceIntegration cartServiceIntegration;

    @Transactional
    public void addNewOrder(String username, OrderDetailsDto orderDetailsDto) {
        CartDto cartDto = cartServiceIntegration.getUserCart(username);
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
        cartServiceIntegration.clearUserCart(username);
    }

    public List<Order> findOrdersByUsername(String username) {
        log.info(username);
        return orderRepository.findAllByUsername(username);
    }
}