package ru.home.aglar.market.core.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.aglar.market.common.dto.StringResponse;
import ru.home.aglar.market.core.backend.integrations.CartServiceIntegration;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {
    private final CartServiceIntegration cartServiceIntegration;

    @GetMapping
    public StringResponse demo() {
        cartServiceIntegration.getUserCart("1");
        return new StringResponse("demo");
    }
}
