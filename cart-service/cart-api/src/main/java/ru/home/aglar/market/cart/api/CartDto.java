package ru.home.aglar.market.cart.api;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {

    private List<CartRecordDto> records;
    private BigDecimal totalPrice;

    public CartDto() {
    }

    public CartDto(List<CartRecordDto> records, BigDecimal totalPrice) {
        this.records = records;
        this.totalPrice = totalPrice;
    }

    public List<CartRecordDto> getRecords() {
        return records;
    }

    public void setRecords(List<CartRecordDto> records) {
        this.records = records;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}