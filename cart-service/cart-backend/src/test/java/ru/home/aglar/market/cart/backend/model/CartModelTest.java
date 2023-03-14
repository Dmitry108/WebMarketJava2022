package ru.home.aglar.market.cart.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.home.aglar.market.core.api.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartModelTest {
    private CartRecord testCartRecord;
    private Cart testCart;

    @BeforeEach
    private void init(){
        testCartRecord = new CartRecord(1L, "Record", BigDecimal.TEN, 3, BigDecimal.valueOf(30));
        List<CartRecord> records = new ArrayList<>();
        records.add(testCartRecord);
        testCart = new Cart(records, testCartRecord.getTotalPrice());
    }

    @Test
    public void cartRecordTest(){
        testCartRecord.changeQuantity(5);
        Assertions.assertEquals(BigDecimal.valueOf(80), testCartRecord.getTotalPrice());
    }

    @Test
    public void addProductIntoCart() {
        ProductDto productDto = new ProductDto(2L, "Apple", BigDecimal.TEN);
        testCart.addProduct(productDto);
        Assertions.assertEquals(2, testCart.getRecords().size());
        Assertions.assertEquals(BigDecimal.valueOf(40), testCart.getTotalPrice());
        testCart.addProduct(productDto);
        Assertions.assertEquals(2, testCart.getRecords().size());
        Assertions.assertEquals(BigDecimal.valueOf(50), testCart.getTotalPrice());
    }

    @Test
    public void increaseIfExists() {
        Assertions.assertTrue(testCart.increaseIfExists(1L));
        Assertions.assertEquals(1, testCart.getRecords().size());
        Assertions.assertEquals(BigDecimal.valueOf(40), testCart.getTotalPrice());
        Assertions.assertFalse(testCart.increaseIfExists(2L));
        Assertions.assertEquals(1, testCart.getRecords().size());
        Assertions.assertEquals(BigDecimal.valueOf(40), testCart.getTotalPrice());
    }

    @Test
    public void clearCart() {
        testCart.clear();
        Assertions.assertEquals(0, testCart.getRecords().size());
        Assertions.assertEquals(BigDecimal.ZERO, testCart.getTotalPrice());
    }

    @Test
    public void decreaseAndDeleteCartRecord() {
        testCart.decreaseProduct(2L);
        Assertions.assertEquals(1, testCart.getRecords().size());
        Assertions.assertEquals(BigDecimal.valueOf(30), testCart.getTotalPrice());
        testCart.decreaseProduct(1L);
        Assertions.assertEquals(1, testCart.getRecords().size());
        Assertions.assertEquals(BigDecimal.valueOf(20), testCart.getTotalPrice());
        testCart.deleteProduct(2L);
        Assertions.assertEquals(1, testCart.getRecords().size());
        Assertions.assertEquals(BigDecimal.valueOf(20), testCart.getTotalPrice());
        testCart.deleteProduct(1L);
        Assertions.assertEquals(0, testCart.getRecords().size());
        Assertions.assertEquals(BigDecimal.ZERO, testCart.getTotalPrice());
    }

    @Test
    public void mergeCarts() {
        List<CartRecord> records = Arrays.asList(
                new CartRecord(2L, "Apple", BigDecimal.valueOf(20), 2, BigDecimal.valueOf(40)),
                new CartRecord(3L, "Orange", BigDecimal.valueOf(30), 3, BigDecimal.valueOf(90)),
                new CartRecord(1L, "Record", BigDecimal.TEN, 1, BigDecimal.TEN));
        Cart anotherCart = new Cart(new ArrayList<>(records), BigDecimal.valueOf(140));
        testCart.merge(anotherCart);
        Assertions.assertEquals(0, anotherCart.getRecords().size());
        Assertions.assertEquals(3, testCart.getRecords().size());
        Assertions.assertEquals(BigDecimal.valueOf(170), testCart.getTotalPrice());
    }
}