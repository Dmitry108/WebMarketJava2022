package ru.home.aglar.market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.aglar.market.entities.Product;

@Data
@NoArgsConstructor
public class CartRecordDto {
    private Long productId;
    private String title;
    private Integer price;
    private Integer quantity;
    private Integer totalPrice;

    public CartRecordDto(Product product) {
        this.productId = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.quantity = 1;
        this.totalPrice = this.price;
    }

    public void changeQuantity(int delta) {
        quantity += delta;
        totalPrice = quantity * price;
    }
}
