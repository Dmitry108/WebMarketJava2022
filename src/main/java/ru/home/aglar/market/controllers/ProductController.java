package ru.home.aglar.market.controllers;

import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.services.ProductService;

import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id).get();
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/low_price")
    public List<Product> getAllProductsByPriceLow(@RequestParam Integer limit) {
        return productService.getAllProductsByPriceLow(limit);
    }

    @GetMapping("/products/between")
    public List<Product> getAllProductsByPriceBetween(@RequestParam Integer min, @RequestParam Integer max) {
        return productService.getAllProductsByPriceBetween(min, max);
    }

    @GetMapping("/products/high_price")
    public List<Product> getAllProductsByPriceHigh(@RequestParam Integer limit) {
        return productService.getAllProductsByPriceHigh(limit);
    }

    @PostMapping("/products")
    public Product addNewProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping("/products/delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}