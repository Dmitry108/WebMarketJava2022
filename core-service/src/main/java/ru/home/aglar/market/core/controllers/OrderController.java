package ru.home.aglar.market.core.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.home.aglar.market.core.converters.OrderConverter;
import ru.home.aglar.market.core.dto.OrderDetailsDto;
import ru.home.aglar.market.core.dto.OrderDto;
import ru.home.aglar.market.core.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.core.aspects.Timer;

import java.security.Principal;
import java.util.List;

//@Timer
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