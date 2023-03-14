package ru.home.aglar.market.core.backend.validations;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.core.api.ProductDto;
import ru.home.aglar.market.core.backend.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {
    public void validate(ProductDto productDto) {
        List<String> errors = new ArrayList<>();
        String title = productDto.getTitle();
        BigDecimal price = productDto.getPrice();
        if (title == null || title.isBlank()) {
            errors.add("title is absent");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            errors.add("price can't be negative");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
