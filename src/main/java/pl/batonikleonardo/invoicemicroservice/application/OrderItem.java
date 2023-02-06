package pl.batonikleonardo.invoicemicroservice.application;

import java.math.BigDecimal;

public record OrderItem(String name, String description, int quantity, BigDecimal price) {
    public static OrderItem create(String name, String description, int quantity, BigDecimal price) {
        return new OrderItem(name, description, quantity, price);
    }
}