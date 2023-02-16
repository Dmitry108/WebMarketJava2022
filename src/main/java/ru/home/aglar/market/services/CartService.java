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
    private Cart currentCart;
    private final ProductService productService;

    @PostConstruct
    private void init() {
        currentCart = new Cart();
    }

    public void addProductById(Long id) {
        if (currentCart.increaseIfExists(id)) return;
        Product product = productService.getProductById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Product with id = %d doesn't found", id)));
        currentCart.addProduct(product);
    }

    public void clear() {
        currentCart.clear();
    }

    public void deleteProductFromCart(Long id) {
        currentCart.deleteProduct(id);
    }

    public void decreaseProductInCart(Long id) {
        currentCart.decreaseProduct(id);
    }
}