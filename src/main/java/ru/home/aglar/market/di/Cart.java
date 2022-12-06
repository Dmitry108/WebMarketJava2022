package ru.home.aglar.market.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.home.aglar.market.model.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@Component
//@Scope("prototype")
public class Cart {
    private List<Product> cartList;
    private ProductRepository repository;

//    @PostConstruct
    private void initCart() {
        cartList = new ArrayList<>();
    }

//    @Autowired
    private void setRepository(ProductRepository repository) {
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