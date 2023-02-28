package ru.home.aglar.market.core.converters;

import ru.home.aglar.market.common.dto.CartRecordDto;
import ru.home.aglar.market.core.entities.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {
    public OrderItem convertFromDto(CartRecordDto cartRecordDto) {
        throw new UnsupportedOperationException();
    }

    public CartRecordDto convertToDto(OrderItem orderItem) {
        return new CartRecordDto(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getTitle(),
                orderItem.getPricePerProduct(),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }
}