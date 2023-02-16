package ru.home.aglar.market.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.home.aglar.market.dto.OrderDto;
import ru.home.aglar.market.entities.Order;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public Order convertFromDto(OrderDto orderDto) {
            throw new UnsupportedOperationException();
    }

    public OrderDto convertToDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getUser().getUsername(),
                order.getOrderItems().stream().map(orderItemConverter::convertToDto).toList(),
                order.getTotalPrice(),
                order.getAddress(),
                order.getPhone()
        );
    }
}