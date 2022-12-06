package ru.home.aglar.market.di.annotations;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.model.Product;

import java.util.List;

@Component
public interface ProductRepository {
    List<Product> getAllProducts();
    Product getProductById(long id);
}