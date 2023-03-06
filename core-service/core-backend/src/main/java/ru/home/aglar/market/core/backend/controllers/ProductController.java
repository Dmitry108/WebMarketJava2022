package ru.home.aglar.market.core.backend.controllers;

import lombok.RequiredArgsConstructor;
import ru.home.aglar.market.common.exceptions.ResourceNotFoundException;
import ru.home.aglar.market.core.api.ProductDto;
import ru.home.aglar.market.core.backend.aspects.Timer;
import ru.home.aglar.market.core.backend.converters.ProductConverter;
import ru.home.aglar.market.core.backend.validations.ProductValidator;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.core.backend.entities.Product;
import ru.home.aglar.market.core.backend.services.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductValidator productValidator;
    private final ProductConverter productConverter;

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productConverter.convertToDto(productService.getProductById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Product with id = %d does not found", id))));
    }

    @Timer
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