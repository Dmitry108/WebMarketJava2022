package ru.home.aglar.market.core.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.home.aglar.market.common.exceptions.AppError;
import ru.home.aglar.market.common.exceptions.ResourceNotFoundException;
import ru.home.aglar.market.core.api.ProductDto;
import ru.home.aglar.market.core.backend.converters.OrderConverter;
import ru.home.aglar.market.core.api.OrderDetailsDto;
import ru.home.aglar.market.core.api.OrderDto;
import ru.home.aglar.market.core.backend.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders", description = "Methods for orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    @Operation(summary = "Method for execute an order", responses = {
            @ApiResponse(description = "Success response", responseCode = "201"),
            @ApiResponse(description = "Product not found exception", responseCode = "404",
                content = @Content(schema = @Schema(implementation = AppError.class)))})
    @ResponseStatus(HttpStatus.CREATED)
    public void doOrder(@RequestHeader @Parameter(description = "Username", required = true) String username,
                        @RequestBody @Parameter(description = "Details of order", required = true) OrderDetailsDto orderDetailsDto) {
        orderService.addNewOrder(username, orderDetailsDto);
    }

    @GetMapping()
    @Operation(summary = "Method for getting a list of orders", responses = {
            @ApiResponse(description = "Success response", responseCode = "200",
                content = @Content(schema = @Schema(implementation = List.class)))})
    public List<OrderDto> getOrder(@RequestHeader @Parameter(description = "Username", required = true) String username) {
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::convertToDto).toList();
    }
}