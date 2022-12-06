package ru.home.aglar.market.di.xml;

import ru.home.aglar.market.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private List<Product> cartList;
    private ProductRepository repository;

    public void initCart() {
        cartList = new ArrayList<>();
    }

    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

    public boolean addProduct(long id) {
        Product product = repository.getProductById(id);
        if (product != null) {
            cartList.add(product);
            return true;
        }
        return false;
    }

    public boolean removeProduct(long id) {
        Product product = repository.getProductById(id);
        if (product != null) {
            cartList.remove(product);
            return true;
        }
        return false;
    }

    public List<Product> getCartList() {
        return Collections.unmodifiableList(cartList);
    }
}