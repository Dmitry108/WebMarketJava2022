package ru.home.aglar.market.repositories;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.entities.Product;

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

    public long generateId() {
        if (products.isEmpty()) return 1;
        long max = products.stream().max((p1, p2) ->
                        p1.getId() > p2.getId() ? 1 : -1)
                .get().getId();
        return ++max;
    }

    @Override
    public Product findProductById(Long id) {
        return products.stream().filter(p -> id.equals(p.getId())).findAny().orElse(null);
    }

    @Override
    public List<Product> findAllProducts() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public boolean deleteProductById(Long id) {
        return products.removeIf(p -> p.getId().equals(id));
    }

    @Override
    public void saveOrUpdate(Product product) {
        products.stream().filter(p -> p.getId().equals(product.getId()))
            .findAny()
            .ifPresentOrElse(p -> {
                p.setTitle(product.getTitle());
                p.setPrice(product.getPrice());
                }, () -> products.add(new Product(generateId(), product.getTitle(), product.getPrice()))
        );
    }
}