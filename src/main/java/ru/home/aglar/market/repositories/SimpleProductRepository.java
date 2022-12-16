package ru.home.aglar.market.repositories;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.model.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SimpleProductRepository implements ProductRepository {
    private List<Product> products;

    @PostConstruct
    private void initRepo() {
        products = new ArrayList<>(List.of(
                new Product(1L, "Звездолёт", 3000),
                new Product(2L, "Летающая тарелка", 4200),
                new Product(3L, "Реактивный самолёт", 2800),
                new Product(4L, "Воздушный шар", 600),
                new Product(5L, "НЛО", 6666)
        ));
    }

    @Override
    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public Product getProductById(long id) {
        Product product = products.stream().filter(p -> id == p.getId()).findAny().orElse(null);
        return new Product(product.getId(), product.getTitle(), product.getCost());
    }

    @Override
    public boolean addProduct(Product product) {
        return products.add(product);
    }

    @Override
    public long generateId() {
        if (products.isEmpty()) return 1;
        long max = products.stream().max((p1, p2) ->
                        p1.getId() > p2.getId() ? 1 : -1)
                .get().getId();
        return ++max;
    }

    @Override
    public void updateProduct(Product product) {
        products.replaceAll(p -> p.getId().equals(product.getId()) ? product : p);
    }

    @Override
    public void deleteProduct(Long id) {
        products.removeIf(p -> p.getId().equals(id));
    }
}