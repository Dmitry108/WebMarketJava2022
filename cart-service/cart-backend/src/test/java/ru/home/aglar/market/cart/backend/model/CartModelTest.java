package ru.home.aglar.market.cart.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.home.aglar.market.core.api.ProductDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartModelTest {
    private CartRecord testCartRecord;
    private Cart testCart;

    @BeforeEach
    private void init(){
        testCartRecord = new CartRecord(1L, "Record", 10, 3, 30);
        List<CartRecord> records = new ArrayList<>();
        records.add(testCartRecord);
        testCart = new Cart(records, testCartRecord.getTotalPrice());
    }

    @Test
    public void cartRecordTest(){
        testCartRecord.changeQuantity(5);
        Assertions.assertEquals(80, testCartRecord.getTotalPrice());
    }

    @Test
    public void addProductIntoCart() {
        ProductDto productDto = new ProductDto(2L, "Apple", 10);
        testCart.addProduct(productDto);
        Assertions.assertEquals(2, testCart.getRecords().size());
        Assertions.assertEquals(40, testCart.getTotalPrice());
        testCart.addProduct(productDto);
        Assertions.assertEquals(2, testCart.getRecords().size());
        Assertions.assertEquals(50, testCart.getTotalPrice());
    }

    @Test
    public void increaseIfExists() {
        Assertions.assertTrue(testCart.increaseIfExists(1L));
        Assertions.assertEquals(1, testCart.getRecords().size());
        Assertions.assertEquals(40, testCart.getTotalPrice());
        Assertions.assertFalse(testCart.increaseIfExists(2L));
        Assertions.assertEquals(1, testCart.getRecords().size());
        Assertions.assertEquals(40, testCart.getTotalPrice());
    }

    @Test
    public void clearCart() {
        testCart.clear();
        Assertions.assertEquals(0, testCart.getRecords().size());
        Assertions.assertEquals(0, testCart.getTotalPrice());
    }

    @Test
    public void decreaseAndDeleteCartRecord() {
        testCart.decreaseProduct(2L);
        Assertions.assertEquals(1, testCart.getRecords().size());
        Assertions.assertEquals(30, testCart.getTotalPrice());
        testCart.decreaseProduct(1L);
        Assertions.assertEquals(1, testCart.getRecords().size());
        Assertions.assertEquals(20, testCart.getTotalPrice());
        testCart.deleteProduct(2L);
        Assertions.assertEquals(1, testCart.getRecords().size());
        Assertions.assertEquals(20, testCart.getTotalPrice());
        testCart.deleteProduct(1L);
        Assertions.assertEquals(0, testCart.getRecords().size());
        Assertions.assertEquals(0, testCart.getTotalPrice());
    }

    @Test
    public void mergeCarts() {
        List<CartRecord> records = Arrays.asList(
                new CartRecord(2L, "Apple", 20, 2, 40),
                new CartRecord(3L, "Orange", 30, 3, 90),
                new CartRecord(1L, "Record", 10, 1, 10));
        Cart anotherCart = new Cart(new ArrayList<>(records), 140);
        testCart.merge(anotherCart);
        Assertions.assertEquals(0, anotherCart.getRecords().size());
        Assertions.assertEquals(3, testCart.getRecords().size());
        Assertions.assertEquals(170, testCart.getTotalPrice());
    }
}