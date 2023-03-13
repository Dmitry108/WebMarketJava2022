package ru.home.aglar.market.core.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Model of order")
public class OrderDto {
    @Schema(description = "Id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(description = "Unique username", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @Schema(description = "List of order's items", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<OrderItemDto> orderItems;
    @Schema(description = "Total price of hole order", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer totalPrice;
    @Schema(description = "User's address", requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;
    @Schema(description = "User's phone", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;

    public OrderDto() {
    }

    public OrderDto(Long id, String username, List<OrderItemDto> orderItems, Integer totalPrice, String address, String phone) {
        this.id = id;
        this.username = username;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.address = address;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}