package pl.batonikleonardo.invoicemicroservice.infrastructure.messaging;

import lombok.Data;

import java.math.BigDecimal;

@Data
class Item {
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;
}
