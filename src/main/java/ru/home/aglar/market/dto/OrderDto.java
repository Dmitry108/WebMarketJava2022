package ru.home.aglar.market.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private String username;
    private List<CartRecordDto> orderItems;
    private Integer totalPrice;
    private String address;
    private String phone;
}