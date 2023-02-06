package pl.batonikleonardo.invoicemicroservice.infrastructure.storage;

import lombok.Data;

import java.math.BigDecimal;

@Data
class InvoiceCreatedEventItem {
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;
}
