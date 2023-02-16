package ru.home.aglar.market.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.home.aglar.market.dto.Cart;
import ru.home.aglar.market.dto.OrderDetailsDto;
import ru.home.aglar.market.entities.Order;
import ru.home.aglar.market.entities.OrderItem;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.entities.User;
import ru.home.aglar.market.exceptions.ResourceNotFoundException;
import ru.home.aglar.market.repositories.OrderRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;
    private final OrderRepository orderRepository;

    @Transactional
    public void addNewOrder(String username, OrderDetailsDto orderDetailsDto) {
        User user = userService.findUserByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with username %s not found", username)));
        Cart cart = cartService.getCurrentCart();
        Order order = new Order();
        order.setUser(user);
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setTotalPrice(cart.getTotalPrice());
        List<OrderItem> orderItems = cart.getRecords().stream()
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
        cart.clear();
    }

    public List<Order> findOrdersByUsername(String username) {
        log.info(username);
        return orderRepository.findAllByUsername(username);
    }
}