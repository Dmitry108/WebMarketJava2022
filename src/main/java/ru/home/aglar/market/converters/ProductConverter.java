package ru.home.aglar.market.converters;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.dto.ProductDto;
import ru.home.aglar.market.entities.Product;

@Component
public class ProductConverter {
    public Product convertFromDto(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice());
    }

    public ProductDto convertToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }
}
