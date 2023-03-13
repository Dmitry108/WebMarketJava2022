package ru.home.aglar.market.cart.api;

import ru.home.aglar.market.common.exceptions.AppError;

public class CartServiceAppError extends AppError {
    public enum CartServiceErrors {
        CART_IS_BROKEN, CART_NOT_FOUND
    }

    public CartServiceAppError(String code, String message) {
        super(code, message);
    }

    public CartServiceAppError() {}
}
