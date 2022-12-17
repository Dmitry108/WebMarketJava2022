package ru.home.aglar.market.repositories;

import ru.home.aglar.market.entities.Product;

import java.util.List;

public interface ProductDao {
    Product findProductById(Long id);
    List<Product> findAllProducts();
    boolean deleteProductById(Long id);
    void saveOrUpdate(Product product);
}
