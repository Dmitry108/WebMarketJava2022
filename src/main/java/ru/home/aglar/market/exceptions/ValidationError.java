package ru.home.aglar.market.exceptions;

import java.util.List;

public class ValidationError {
    private int statusCode;
    private List<String> errors;

    public ValidationError(int statusCode, List<String> errors) {
        this.statusCode = statusCode;
        this.errors = errors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
