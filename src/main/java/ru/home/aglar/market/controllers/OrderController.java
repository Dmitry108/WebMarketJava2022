package ru.home.aglar.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.dto.OrderRequest;
import ru.home.aglar.market.dto.OrderResponse;
import ru.home.aglar.market.services.OrderService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void doOrder(@RequestBody OrderRequest orderRequest, Principal principal) {
        orderService.addNewOrder(principal.getName(), orderRequest.getCart());
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        return orderService.getOrderFullInfo(id);
    }
}