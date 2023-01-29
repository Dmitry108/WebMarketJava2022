package ru.home.aglar.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.aglar.market.dto.CartRecordDto;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.repositories.CartRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public List<CartRecordDto> getCartRecords() {
        return cartRepository.getCartRecords();
    }

    public void addProductToCart(Product product) {
        cartRepository.addProduct(product);
    }

    public void deleteProductFromCart(Long id, Integer delta) {
        if (delta == null) {
            cartRepository.deleteProduct(id);
        } else {
            cartRepository.changeCount(id, -1 * delta);
        }
    }
}