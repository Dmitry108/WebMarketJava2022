package ru.home.aglar.market.cart.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRecord {
    private Long productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;

    public void changeQuantity(int delta) {
        quantity += delta;
        totalPrice = price.multiply(BigDecimal.valueOf(quantity));
    }
}