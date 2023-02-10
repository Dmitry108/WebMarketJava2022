package ru.home.aglar.market.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.aglar.market.dto.Cart;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.exceptions.ResourceNotFoundException;
import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Getter
public class CartService {
    private Cart cart;
    private final ProductService productService;

    @PostConstruct
    private void init() {
        cart = new Cart();
    }

    public void addProductById(Long id) {
        if (cart.increaseIfExists(id)) return;
        Product product = productService.getProductById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Product with id = %d doesn't found", id)));
        cart.addProduct(product);
    }

    public void clear() {
        cart.clear();
    }

    public void deleteProductFromCart(Long id) {
        cart.deleteProduct(id);
    }

    public void decreaseProductInCart(Long id) {
        cart.decreaseProduct(id);
    }
}