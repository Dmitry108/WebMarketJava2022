package ru.home.aglar.market.core.api;

import ru.home.aglar.market.common.exceptions.AppError;

public class CoreServiceAppError extends AppError {
    public enum CoreServiceErrors {
        RESOURCE_NOT_FOUND, CART_SERVICE_INTEGRATION
    }

    public CoreServiceAppError(String code, String message) {
        super(code, message);
    }

    public CoreServiceAppError() {}
}
