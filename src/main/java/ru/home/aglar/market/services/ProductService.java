package ru.home.aglar.market.services;

import org.springframework.stereotype.Service;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.repositories.ProductRepository;

import java.util.List;
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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAllProductsByPriceLow(Integer limit) {
        return productRepository.findAllByPriceLow(limit);
    }

    public List<Product> getAllProductsByPriceBetween(Integer min, Integer max) {
        return productRepository.findAllByPriceBetween(min, max);
    }

    public List<Product> getAllProductsByPriceHigh(Integer limit) {
        return productRepository.findAllByPriceHigh(limit);
    }


    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}