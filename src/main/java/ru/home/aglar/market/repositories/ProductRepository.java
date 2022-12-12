package ru.home.aglar.market.repositories;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.model.Product;

import java.util.Collection;
import java.util.List;

@Component
public interface ProductRepository {
    List<Product> getAllProducts();
    Product getProductById(long id);
    boolean addProduct(Product product);
    boolean addAllProducts(Collection<Product> products);
}