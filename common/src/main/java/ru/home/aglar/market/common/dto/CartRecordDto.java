package ru.home.aglar.market.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRecordDto {
    private Long productId;
    private String title;
    private Integer price;
    private Integer quantity;
    private Integer totalPrice;

    public void changeQuantity(int delta) {
        quantity += delta;
        totalPrice = quantity * price;
    }
}