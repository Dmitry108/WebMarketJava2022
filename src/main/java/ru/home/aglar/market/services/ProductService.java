package ru.home.aglar.market.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.repositories.ProductDAO;

import java.util.List;

@Service
public class ProductService {
    private final ProductDAO productRepository;

    public ProductService(@Qualifier("ProductDaoComponent") ProductDAO productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAllProducts();
    }

    public Product getProductById(Long id) {
        return  productRepository.findProductById(id);
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