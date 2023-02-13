package ru.home.aglar.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.aglar.market.converters.OrderItemConverter;
import ru.home.aglar.market.converters.UserConverter;
import ru.home.aglar.market.dto.Cart;
import ru.home.aglar.market.dto.CartRecordDto;
import ru.home.aglar.market.dto.OrderResponse;
import ru.home.aglar.market.entities.Order;
import ru.home.aglar.market.entities.OrderItem;
import ru.home.aglar.market.exceptions.ResourceNotFoundException;
import ru.home.aglar.market.repositories.OrderItemRepository;
import ru.home.aglar.market.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserService userService;
    private final ProductService productService;
    private final OrderItemConverter orderItemConverter;
    private final UserConverter userConverter;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    public void addNewOrder(Long userId, Cart cart) {
        List<CartRecordDto> itemDtoList = cart.getRecords();
        LocalDateTime dateTime = LocalDateTime.now();
        Order order = new Order(null,
                userService.findUserById(userId).orElseThrow(() ->
                        new ResourceNotFoundException(String.format("User with id = %d not found", userId))),
                cart.getTotalPrice(), dateTime, dateTime);
        List<OrderItem> orderItems = itemDtoList.stream().map(itemDto -> orderItemConverter.convertFromDto(itemDto, order,
                productService.getProductById(itemDto.getProductId()).orElseThrow(() ->
                        new ResourceNotFoundException("Product not found")))).toList();
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
    }

    public OrderResponse getOrderFullInfo(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Order with id = %d not found", id)));
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(id);
        return new OrderResponse(order.getId(), userConverter.convertToDto(order.getUser()),
                orderItems.stream().map(orderItemConverter::convertToDto).toList(), order.getTotalPrice(),
                order.getCreatedAt(), order.getUpdatedAt());
    }
}