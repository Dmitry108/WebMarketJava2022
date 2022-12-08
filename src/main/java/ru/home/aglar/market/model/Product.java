package ru.home.aglar.market.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Product {
    private long id;
    private String title;
    private int cost;

    @Override
    public String toString() {
        return String.format("%d: %s - %d ME", id, title, cost);
    }
}