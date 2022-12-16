package ru.home.aglar.market.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.home.aglar.market.model.Product;
import ru.home.aglar.market.services.ProductService;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/change_cost")
    public void changeCost(@RequestParam Long id, @RequestParam Integer delta) {
        productService.changeProductCost(id, delta);
    }
}
