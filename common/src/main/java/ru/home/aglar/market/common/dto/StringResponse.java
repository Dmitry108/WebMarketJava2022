package ru.home.aglar.market.common.dto;

public class StringResponse {
    private String value;

    public StringResponse(String value) {
        this.value = value;
    }

    public StringResponse() {}

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
