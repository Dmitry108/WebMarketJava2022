package ru.home.aglar.market.core.backend.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import ru.home.aglar.market.core.backend.entities.Product;
import ru.home.aglar.market.core.backend.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void getProductByIdTest() {
        Optional<Product> product = Optional.of(new Product(1L, "Apple", BigDecimal.TEN));
        Mockito.doReturn(product).when(productRepository).findById(1L);
        Assertions.assertEquals(product, productService.getProductById(1L));
    }

    @Test
    public void getAllProductsTest() {
        List<Product> products = List.of(
                new Product(1L, "Apple", BigDecimal.TEN),
                new Product(2L, "Orange", BigDecimal.valueOf(20)));
        Page<Product> page = new PageImpl<>(products);

        Mockito.when(productRepository.findAll(
                ArgumentMatchers.isA(Specification.class),
                ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(page);
        Assertions.assertEquals(page, productService.getAllProducts(1, null, null));
    }

    @Test
    public void deleteProductByIdTest() {
        productService.deleteProductById(1L);
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void updateProductTest () {
        Product product = new Product(1L, "Apple", BigDecimal.TEN);
        productService.updateProduct(product);
        Mockito.verify(productRepository, Mockito.times(1))
                .save(ArgumentMatchers.eq(product));
    }
}
