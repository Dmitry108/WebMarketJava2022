package ru.home.aglar.market.core.backend.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void doOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto) {
        orderService.addNewOrder(username, orderDetailsDto);
    }

    @GetMapping()
    public List<OrderDto> getOrder(@RequestHeader String username) {
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::convertToDto).toList();
    }
}