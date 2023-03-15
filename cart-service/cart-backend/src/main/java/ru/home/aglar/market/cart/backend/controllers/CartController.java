package ru.home.aglar.market.cart.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.cart.api.CartDto;
import ru.home.aglar.market.cart.backend.converters.CartConverter;
import ru.home.aglar.market.cart.backend.exceptions.CartIsBrokenException;
import ru.home.aglar.market.cart.backend.intergations.ProductServiceIntegration;
import ru.home.aglar.market.cart.backend.services.CartService;
import ru.home.aglar.market.common.dto.StringResponse;
import ru.home.aglar.market.common.exceptions.AppError;
import ru.home.aglar.market.core.api.ProductDto;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Carts", description = "Methods for a working with a cart")
public class CartController {
    private final ProductServiceIntegration productServiceIntegration;
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/{uuid}")
    @Operation(summary = "Method of getting user's cart by username or guest's cart by uuid", responses = {
            @ApiResponse(description = "Success response", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = CartDto.class)))})
    public CartDto getCart(@RequestHeader(required = false) @Parameter(description = "User's username") String username,
                           @PathVariable @Parameter(description = "Uuid of a guest's cart", required = true) String uuid) {
        return cartConverter.convertToDto(cartService.getCurrentCart(getCartKey(username, uuid)));
    }

    @GetMapping("/generate")
    @Operation(summary = "Method of generation uuid for cart", responses = {
            @ApiResponse(description = "Success response", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = StringResponse.class)))})
    public StringResponse generateCartUuid() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/{uuid}/add/{id}")
    @Operation(summary = "Method of adding product to cart", responses = {
            @ApiResponse(description = "Success response", responseCode = "200")})
    //Integration exceptions
    public void addProductToCart(@RequestHeader(required = false) @Parameter(description = "User's username") String username,
                                 @PathVariable @Parameter(description = "Uuid of a guest's cart", required = true) String uuid,
                                 @PathVariable @Parameter(description = "Id of a product") Long id) {
        ProductDto productdto = productServiceIntegration.getProductById(id);
        cartService.addProduct(getCartKey(username, uuid), productdto);
    }

    @GetMapping("{uuid}/clear")
    @Operation(summary = "Method of clearing of a cart", responses = {
            @ApiResponse(description = "Success response", responseCode = "200")})
    public void clear(@RequestHeader(required = false) @Parameter(description = "User's username") String username,
                      @PathVariable @Parameter(description = "Uuid of a guest's cart", required = true) String uuid) {
        cartService.clear(getCartKey(username, uuid));
    }

    @GetMapping("/{uuid}/delete/{id}")
    @Operation(summary = "Method of deleting of a cart", responses = {
            @ApiResponse(description = "Success response", responseCode = "200")})
    public void deleteProductFromCart(@RequestHeader(required = false) @Parameter(description = "User's username") String username,
                                      @PathVariable @Parameter(description = "Uuid of a guest's cart", required = true) String uuid,
                                      @PathVariable @Parameter(description = "Id of a product", required = true) Long id) {
        cartService.deleteProductFromCart(getCartKey(username, uuid), id);
    }

    @GetMapping("/{uuid}/decrease/{id}")
    @Operation(summary = "Method of a decreasing quantity products in a cart", responses = {
            @ApiResponse(description = "Success response", responseCode = "200")})
    public void decreaseProductInCart(@RequestHeader(required = false) @Parameter(description = "User's username") String username,
                                      @PathVariable @Parameter(description = "Uuid of a guest's cart", required = true) String uuid,
                                      @PathVariable @Parameter(description = "Id of a product", required = true) Long id) {
        cartService.decreaseProductInCart(getCartKey(username, uuid), id);
    }

    @GetMapping("/{uuid}/merge")
    @Operation(summary = "Method of a merging user's and his guest's carts", responses = {
            @ApiResponse(description = "Success response", responseCode = "200")})
    public void mergeCarts(@RequestHeader @Parameter(description = "User's username", required = true) String username,
                           @PathVariable @Parameter(description = "Uuid of a guest's cart", required = true) String uuid) {
        String userCartKey = getCartKey(username, null);
        String guestCartKey = getCartKey(null, uuid);
        cartService.merge(userCartKey, guestCartKey);
    }

    private String getCartKey(String username, String uuid) {
        return cartService.generateCartFromSuffix(username == null ? uuid : username);
    }
}