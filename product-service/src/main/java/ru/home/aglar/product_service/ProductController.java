package ru.home.aglar.product_service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts().stream().map(productConverter::convertToDto).toList();
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