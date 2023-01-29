package ru.home.aglar.market.repositories;

import org.springframework.stereotype.Component;
import ru.home.aglar.market.dto.CartRecordDto;
import ru.home.aglar.market.entities.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CartRepository {
    private final List<CartRecordDto> records;

    public CartRepository() {
        records = new ArrayList<>();
    }

    public void addProduct(Product product) {
        records.stream()
                .filter(record -> record.getProduct().getId().equals(product.getId()))
                .findAny()
                .ifPresentOrElse(CartRecordDto::increaseCount,
                        () -> records.add(new CartRecordDto(product)));
    }

    public List<CartRecordDto> getCartRecords() {
        return Collections.unmodifiableList(records);
    }

    public void deleteProduct(Long id) {
        records.removeIf(record -> record.getProduct().getId().equals(id));
    }

    public void changeCount(Long id, Integer delta) {
        records.stream().filter(r -> r.getProduct().getId().equals(id))
                .findAny()
                .ifPresent(r -> {
                    if (r.getCount() + delta <= 0) records.remove(r);
                    else r.changeCount(delta);
                });
    }
}