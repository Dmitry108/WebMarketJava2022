package ru.home.aglar.market.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class OrderResponse {
    private Long id;
    private UserDto user;
    private List<CartRecordDto> orderItems;
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}