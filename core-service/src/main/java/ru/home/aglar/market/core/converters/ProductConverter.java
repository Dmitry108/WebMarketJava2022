package ru.home.aglar.market.core.converters;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.core.dto.ProductDto;
import ru.home.aglar.market.core.entities.Product;

@Component
public class ProductConverter {
    public Product convertFromDto(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice());
    }

    public ProductDto convertToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }
}
