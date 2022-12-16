package ru.home.aglar.market.services;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.model.Product;
import ru.home.aglar.market.repositories.ProductRepository;

import java.util.List;

@Component
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.getAllProducts();
    }
}
