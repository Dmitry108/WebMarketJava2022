package ru.home.aglar.market.core.api;

import java.util.List;

public class OrderDto {
    private Long id;
    private String username;
    private List<OrderItemDto> orderItems;
    private Integer totalPrice;
    private String address;
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