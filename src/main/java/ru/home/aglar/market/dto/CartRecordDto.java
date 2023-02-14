package ru.home.aglar.market.dto;

import ru.home.aglar.market.entities.Product;

public class CartRecordDto {
    private Product product;
    private int count;

    public Integer getTotalPrice() {
        return product.getPrice() * count;
    }

    public CartRecordDto(Product product) {
        this.product = product;
        this.count = 1;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void increaseCount() {
        ++count;
    }

    public void decreaseCount() {
        --count;
    }

    public void changeCount(int delta) {
        count += delta;
    }
}
