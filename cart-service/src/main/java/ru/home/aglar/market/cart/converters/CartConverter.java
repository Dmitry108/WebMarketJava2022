package ru.home.aglar.market.cart.converters;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.cart.dto.Cart;
import ru.home.aglar.market.common.dto.CartDto;

@Component
public class CartConverter {

    public CartDto convertToDto(Cart cart) {
        return new CartDto(cart.getRecords(), cart.getTotalPrice());
    }

    public Cart convertFromDto(CartDto cartDto) {
        return new Cart(cartDto.getRecords(), cartDto.getTotalPrice());
    }
}