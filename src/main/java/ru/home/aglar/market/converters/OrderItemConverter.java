package ru.home.aglar.market.converters;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.dto.CartRecordDto;
import ru.home.aglar.market.entities.Order;
import ru.home.aglar.market.entities.OrderItem;
import ru.home.aglar.market.entities.Product;

@Component
public class OrderItemConverter {
    public OrderItem convertFromDto(CartRecordDto cartRecordDto, Order order, Product product) {
        return new OrderItem(null, order, product, cartRecordDto.getQuantity(),
                cartRecordDto.getPrice(), cartRecordDto.getTotalPrice());
    }

    public CartRecordDto convertToDto(OrderItem orderItem) {
        return new CartRecordDto(orderItem.getProduct().getId(), orderItem.getProduct().getTitle(),
                orderItem.getPricePerProduct(), orderItem.getQuantity(), orderItem.getPrice());
    }
}