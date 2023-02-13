package ru.home.aglar.market.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequest {
    private Long userId;
    private Cart cart;
}