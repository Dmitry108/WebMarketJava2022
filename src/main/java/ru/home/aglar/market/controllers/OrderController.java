package ru.home.aglar.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.home.aglar.market.converters.OrderConverter;
import ru.home.aglar.market.dto.OrderDetailsDto;
import ru.home.aglar.market.dto.OrderDto;
import ru.home.aglar.market.services.OrderService;
import ru.home.aglar.market.services.UserService;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;
    private final UserService userService;

    @PostMapping
    //вернется в ответе 201
    @ResponseStatus(HttpStatus.CREATED)
    public void doOrder(@RequestBody OrderDetailsDto orderDetailsDto, Principal principal) {
        orderService.addNewOrder(principal.getName(), orderDetailsDto);
    }

    @GetMapping()
    public List<OrderDto> getOrder(Principal principal) {
        return orderService.findOrdersByUsername(principal.getName()).stream()
                .map(orderConverter::convertToDto).toList();
    }
}