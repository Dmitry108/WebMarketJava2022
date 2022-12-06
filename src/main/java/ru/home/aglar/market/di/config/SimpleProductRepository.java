package ru.home.aglar.market.di.config;

import ru.home.aglar.market.model.Product;
import java.util.List;

public class SimpleProductRepository implements ProductRepository {
    private List<Product> products;

    public void initRepo() {
        products = List.of(
                new Product(1, "Звездолёт", 3000),
                new Product(2, "Летающая тарелка", 4200),
                new Product(3, "Реактивный самолёт", 2800),
                new Product(4, "Воздушный шар", 600),
                new Product(5, "НЛО", 6666)
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProductById(long id) {
        return products.stream().filter(p -> id == p.getId()).findAny().orElse(null);
    }
}