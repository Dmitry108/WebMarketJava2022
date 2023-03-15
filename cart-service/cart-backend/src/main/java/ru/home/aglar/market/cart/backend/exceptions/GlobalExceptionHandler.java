package ru.home.aglar.market.cart.backend.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.home.aglar.market.cart.api.CartServiceAppError;
import ru.home.aglar.market.common.exceptions.AppError;
import ru.home.aglar.market.common.exceptions.ResourceNotFoundException;
import ru.home.aglar.market.core.api.CoreServiceAppError;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<CartServiceAppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CartServiceAppError(CartServiceAppError.CartServiceErrors.CART_NOT_FOUND.name(),
                e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CartServiceAppError> catchCartIsBrokenException(CartIsBrokenException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(
                new CartServiceAppError(CartServiceAppError.CartServiceErrors.CART_IS_BROKEN.name(),
                e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<CartServiceAppError> catchCoreServiceIntegrationException(CoreServiceIntegrationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CartServiceAppError(CartServiceAppError.CartServiceErrors.CORE_SERVICE_INTEGRATION.name(),
                e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
