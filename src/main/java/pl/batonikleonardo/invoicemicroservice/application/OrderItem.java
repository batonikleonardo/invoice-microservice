package pl.batonikleonardo.invoicemicroservice.application;

import java.math.BigDecimal;

record OrderItem(String name, String description, int quantity, BigDecimal price) {
}