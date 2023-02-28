package ru.home.aglar.market.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import ru.home.aglar.market.common.dto.CartRecordDto;
import ru.home.aglar.market.common.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Cart {
    private List<CartRecordDto> records;
    private Integer totalPrice;

    public Cart() {
        records = new ArrayList<>();
    }

    private void recalculate() {
        totalPrice = 0;
        records.forEach(record -> totalPrice += record.getTotalPrice());
    }

    public void addProduct(ProductDto productDto) {
        records.stream()
                .filter(record -> record.getProductId().equals(productDto.getId()))
                .findAny()
                .ifPresentOrElse(record -> record.changeQuantity(1),
                        () -> records.add(new CartRecordDto(
                                productDto.getId(), productDto.getTitle(),
                                productDto.getPrice(), 1, productDto.getPrice())));
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

    public void merge(Cart anotherCart) {
        anotherCart.getRecords().forEach(recordFromAnotherCart -> records.stream()
                .filter(record -> record.getProductId().equals(recordFromAnotherCart.getProductId()))
                .findAny()
                .ifPresentOrElse(
                        record -> record.changeQuantity(recordFromAnotherCart.getQuantity()),
                        () -> records.add(recordFromAnotherCart)));
        recalculate();
        anotherCart.clear();
    }
}