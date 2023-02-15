package ru.home.aglar.market.converters;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.dto.CartRecordDto;
import ru.home.aglar.market.entities.OrderItem;

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