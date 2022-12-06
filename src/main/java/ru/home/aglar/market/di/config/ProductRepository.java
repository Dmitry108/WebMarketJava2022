package ru.home.aglar.market.di.config;

import ru.home.aglar.market.model.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();
    Product getProductById(long id);
}