package ru.home.aglar.market.cart.backend.converters;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.cart.api.CartDto;
import ru.home.aglar.market.cart.api.CartRecordDto;
import ru.home.aglar.market.cart.backend.model.Cart;
import ru.home.aglar.market.cart.backend.model.CartRecord;

import java.util.List;

@Component
public class CartConverter {

    public CartDto convertToDto(Cart cart) {
        List<CartRecordDto> recordDtoList = cart.getRecords().stream().map(record ->
                new CartRecordDto(record.getProductId(), record.getTitle(), record.getPrice(),
                        record.getQuantity(), record.getTotalPrice())).toList();
        return new CartDto(recordDtoList, cart.getTotalPrice());
    }

    public Cart convertFromDto(CartDto cartDto) {
        List<CartRecord> recordList = cartDto.getRecords().stream().map(recordDto ->
                new CartRecord(recordDto.getProductId(), recordDto.getTitle(), recordDto.getPrice(),
                        recordDto.getQuantity(), recordDto.getTotalPrice())).toList();
        return new Cart(recordList, cartDto.getTotalPrice());
    }
}