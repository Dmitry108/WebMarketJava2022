package ru.home.aglar.market.dto;

import lombok.Getter;
import ru.home.aglar.market.entities.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Cart {
    private final List<CartRecordDto> records;
    private Integer totalPrice;

    public Cart() {
        records = new ArrayList<>();
    }

    private void recalculate() {
        totalPrice = 0;
        records.forEach(record -> totalPrice += record.getTotalPrice());
    }

    public void addProduct(Product product) {
        records.stream()
                .filter(record -> record.getProductId().equals(product.getId()))
                .findAny()
                .ifPresentOrElse(record -> record.changeQuantity(1),
                        () -> records.add(new CartRecordDto(product)));
        recalculate();
    }

    public boolean increaseIfExists(Long id) {
        CartRecordDto recordDto = records.stream().filter(record -> record.getProductId().equals(id))
                .findAny().orElse(null);
        if (recordDto == null) return false;
        recordDto.changeQuantity(1);
        recalculate();
        return true;
    }

    public List<CartRecordDto> getRecords() {
        return Collections.unmodifiableList(records);
    }

    public void clear() {
        records.clear();
        totalPrice = 0;
    }
    public void deleteProduct(Long id) {
        records.removeIf(record -> record.getProductId().equals(id));
        recalculate();
    }

    public void decreaseProduct(Long id) {
        records.stream().filter(r -> r.getProductId().equals(id))
                .findAny()
                .ifPresent(r -> {
                    if (r.getQuantity() == 1) records.remove(r);
                    else r.changeQuantity(-1);
                });
        recalculate();
    }
}