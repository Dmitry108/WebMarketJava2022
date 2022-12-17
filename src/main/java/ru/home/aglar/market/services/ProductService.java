package ru.home.aglar.market.services;

import org.springframework.stereotype.Service;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.repositories.ProductRepository;
import ru.home.aglar.market.repositories.SimpleProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAllProducts();
    }

    public boolean changeProductCost(Long id, Integer delta) {
        Product product = productRepository.findProductById(id);
        int newCost = product.getPrice() + delta;
        if (newCost >= 0) {
            product.setPrice(newCost);
            productRepository.saveOrUpdate(product);
            return true;
        }
        return false;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteProductById(id);
    }
}