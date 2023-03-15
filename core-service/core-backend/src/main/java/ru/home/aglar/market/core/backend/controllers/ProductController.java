package ru.home.aglar.market.core.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import ru.home.aglar.market.common.exceptions.AppError;
import ru.home.aglar.market.common.exceptions.ResourceNotFoundException;
import ru.home.aglar.market.core.api.ProductDto;
import ru.home.aglar.market.core.backend.aspects.Timer;
import ru.home.aglar.market.core.backend.converters.ProductConverter;
import ru.home.aglar.market.core.backend.exceptions.ValidationError;
import ru.home.aglar.market.core.backend.validations.ProductValidator;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.core.backend.services.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Methods for products")
public class ProductController {
    private final ProductService productService;
    private final ProductValidator productValidator;
    private final ProductConverter productConverter;

    @GetMapping("/{id}")
    @Operation(summary = "Request for getting product by id", responses = {
            @ApiResponse(description = "Success response", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(description = "Product not found exception", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = AppError.class)))})
    public ProductDto getProductById(@PathVariable @Parameter(description = "Id of product", required = true) Long id) {
        return productConverter.convertToDto(productService.getProductById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Product with id = %d does not found", id))));
    }

    @Timer
    @GetMapping
    @Operation(summary = "Request for getting page of products", responses = {@ApiResponse(
            description = "Success response", responseCode = "200",
            content = @Content(schema = @Schema(implementation = Page.class)))})
    public Page<ProductDto> getAllProducts(@RequestParam(name = "p", defaultValue = "1") Integer page,
                                           @RequestParam(name = "min_price", required = false) Integer minPrice,
                                           @RequestParam(name = "max_price", required = false) Integer maxPrice) {
        if (page < 1) page = 1;
        return productService.getAllProducts(page, minPrice, maxPrice).map(productConverter::convertToDto);
    }

    @PostMapping
    @Operation(summary = "Adding new product into database", responses = {
            @ApiResponse(description = "Success response", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(description = "Product validation errors", responseCode = "401",
                    content = @Content(schema = @Schema(implementation = ValidationError.class)))})
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addNewProduct(@RequestBody @Parameter(description = "Product", required = true) ProductDto productDto) {
        productValidator.validate(productDto);
        return productConverter.convertToDto(productService.addProduct(productConverter.convertFromDto(productDto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleting product from database by id", responses = {@ApiResponse(
            description = "Success response", responseCode = "200")})
    public void deleteProductById(@PathVariable @Parameter(description = "Id of a deleting product") Long id) {
        productService.deleteProductById(id);
    }

    @PutMapping
    @Operation(summary = "Updating product in database", responses = {
            @ApiResponse(description = "Success response", responseCode = "200"),
            @ApiResponse(description = "Product validation errors", responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ValidationError.class)))})
    public void updateProduct(@RequestBody @Parameter(description = "Product for updating") ProductDto productDto) {
        productValidator.validate(productDto);
        productService.updateProduct(productConverter.convertFromDto(productDto));
    }
}