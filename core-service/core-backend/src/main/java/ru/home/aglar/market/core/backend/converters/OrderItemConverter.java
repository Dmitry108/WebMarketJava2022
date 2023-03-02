package ru.home.aglar.market.core.backend.converters;

import ru.home.aglar.market.core.api.OrderItemDto;
import ru.home.aglar.market.core.backend.entities.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {
    public OrderItem convertFromDto(OrderItemDto cartRecordDto) {
        throw new UnsupportedOperationException();
    }

    public OrderItemDto convertToDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getTitle(),
                orderItem.getPricePerProduct(),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }
}