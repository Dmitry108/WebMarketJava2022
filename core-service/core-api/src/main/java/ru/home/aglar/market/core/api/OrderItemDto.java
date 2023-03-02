package ru.home.aglar.market.core.api;

public class OrderItemDto {
    private Long productId;
    private String title;
    private Integer price;
    private Integer quantity;
    private Integer totalPrice;

    public OrderItemDto() {
    }

    public OrderItemDto(Long productId, String title, Integer price, Integer quantity, Integer totalPrice) {
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}