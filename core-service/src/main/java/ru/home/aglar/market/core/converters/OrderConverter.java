package ru.home.aglar.market.core.converters;

import lombok.RequiredArgsConstructor;
import ru.home.aglar.market.core.dto.OrderDto;
import ru.home.aglar.market.core.entities.Order;
import org.springframework.stereotype.Component;

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
                order.getUsername(),
                order.getOrderItems().stream().map(orderItemConverter::convertToDto).toList(),
                order.getTotalPrice(),
                order.getAddress(),
                order.getPhone()
        );
    }
}