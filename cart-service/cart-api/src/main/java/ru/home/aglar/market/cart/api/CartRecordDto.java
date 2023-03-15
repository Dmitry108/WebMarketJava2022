package ru.home.aglar.market.cart.api;

import java.math.BigDecimal;

public class CartRecordDto {
    private Long productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;

    public CartRecordDto() {
    }

    public CartRecordDto(Long productId, String title,
                         BigDecimal price, Integer quantity, BigDecimal totalPrice) {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}