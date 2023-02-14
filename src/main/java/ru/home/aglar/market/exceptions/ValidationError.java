package ru.home.aglar.market.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ValidationError {
    private int statusCode;
    private List<String> errors;
}
