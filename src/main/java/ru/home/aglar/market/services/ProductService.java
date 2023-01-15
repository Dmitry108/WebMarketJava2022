package ru.home.aglar.market.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.repositories.ProductRepository;
import ru.home.aglar.market.specifications.ProductSpecifications;

import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Page<Product> getAllProducts(Integer page, Integer minPrice, Integer maxPrice) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThen(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.priceLesserOrEqualsThen(maxPrice));
        }
        return productRepository.findAll(spec, PageRequest.of(page - 1, 6));
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }
}