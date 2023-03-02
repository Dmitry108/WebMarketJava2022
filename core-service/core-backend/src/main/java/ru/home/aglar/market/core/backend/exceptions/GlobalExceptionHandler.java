package ru.home.aglar.market.core.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.home.aglar.market.common.exceptions.AppError;
import ru.home.aglar.market.common.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ValidationError> catchValidationException(ValidationException e) {
        return new ResponseEntity<>(new ValidationError(HttpStatus.BAD_REQUEST.value(), e.getErrors()),
                HttpStatus.BAD_REQUEST);
    }
}
