package ru.home.aglar.market.core.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class OrderItemDto {
    @Schema(description = "Id of product", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long productId;
    @Schema(description = "Title", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(description = "Price of one product", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;
    @Schema(description = "Quantity of products", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;
    @Schema(description = "Total price all products in this item", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal totalPrice;

    public OrderItemDto() {
    }

    public OrderItemDto(Long productId, String title, BigDecimal price, Integer quantity, BigDecimal totalPrice) {
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