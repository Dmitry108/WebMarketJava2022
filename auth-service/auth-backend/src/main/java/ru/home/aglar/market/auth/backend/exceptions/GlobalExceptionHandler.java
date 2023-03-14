package ru.home.aglar.market.auth.backend.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.home.aglar.auth.api.AuthServiceAppError;
import ru.home.aglar.market.common.exceptions.ResourceNotFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AuthServiceAppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AuthServiceAppError(AuthServiceAppError.AuthServiceErrors.RESOURCE_NOT_FOUND.name(),
                e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
