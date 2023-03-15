package ru.home.aglar.market.core.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Model of order details")
public class OrderDetailsDto {
    @Schema(description = "User's phone", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;
    @Schema(description = "User's address", requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;

    public OrderDetailsDto() {
    }

    public OrderDetailsDto(String phone, String address) {
        this.phone = phone;
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}