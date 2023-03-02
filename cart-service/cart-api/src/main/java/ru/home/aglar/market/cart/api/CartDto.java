package ru.home.aglar.market.cart.api;

import java.util.List;

public class CartDto {
    private List<CartRecordDto> records;
    private Integer totalPrice;

    public CartDto() {
    }

    public CartDto(List<CartRecordDto> records, Integer totalPrice) {
        this.records = records;
        this.totalPrice = totalPrice;
    }

    public List<CartRecordDto> getRecords() {
        return records;
    }

    public void setRecords(List<CartRecordDto> records) {
        this.records = records;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}