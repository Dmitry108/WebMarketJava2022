package ru.home.aglar.market.validations;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.dto.ProductDto;
import ru.home.aglar.market.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {
    public void validate(ProductDto productDto) {
        List<String> errors = new ArrayList<>();
        String title = productDto.getTitle();
        Integer price = productDto.getPrice();
        if (title == null || title.isBlank()) {
            errors.add("title is absent");
        }
        if (price < 0) {
            errors.add("price can't be negative");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
