package ru.home.aglar.market.controllers;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.converters.ProductConverter;
import ru.home.aglar.market.dto.ProductDto;
import ru.home.aglar.market.entities.Product;
import ru.home.aglar.market.exceptions.ResourceNotFoundException;
import ru.home.aglar.market.services.ProductService;
import ru.home.aglar.market.validations.ProductValidator;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private ProductService productService;
    private ProductValidator productValidator;
    private ProductConverter productConverter;

    public ProductController(ProductService productService, ProductValidator productValidator, ProductConverter productConverter) {
        this.productService = productService;
        this.productValidator = productValidator;
        this.productConverter = productConverter;
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productConverter.convertToDto(productService.getProductById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Product with id = %d does not found", id))));
    }

    @GetMapping
    public Page<ProductDto> getAllProducts(@RequestParam(name = "p", defaultValue = "1") Integer page,
                                           @RequestParam(name = "min_price", required = false) Integer minPrice,
                                           @RequestParam(name = "max_price", required = false) Integer maxPrice) {
        if (page < 1) page = 1;
        return productService.getAllProducts(page, minPrice, maxPrice).map(productConverter::convertToDto);
    }

    @PostMapping
    public Product addNewProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        return productService.addProduct(productConverter.convertFromDto(productDto));
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @PutMapping
    public void updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        productService.updateProduct(productConverter.convertFromDto(productDto));
    }
}