package ru.home.aglar.market.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AppError {
    private int statusCode;
    private String message;
}