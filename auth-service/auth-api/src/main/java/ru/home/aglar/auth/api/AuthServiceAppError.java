package ru.home.aglar.auth.api;

import ru.home.aglar.market.common.exceptions.AppError;

public class AuthServiceAppError extends AppError {
    public enum AuthServiceErrors {
        RESOURCE_NOT_FOUND
    }

    public AuthServiceAppError(String code, String message) {
        super(code, message);
    }

    public AuthServiceAppError() {}
}
