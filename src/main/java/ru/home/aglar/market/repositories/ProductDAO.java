package ru.home.aglar.market.repositories;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.entities.Product;

import java.util.List;

@Component
public interface ProductDAO {
    Product findProductById(Long id);
    List<Product> findAllProducts();
    boolean deleteProductById(Long id);
    void saveOrUpdate(Product product);
}