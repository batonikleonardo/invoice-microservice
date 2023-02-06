package pl.batonikleonardo.invoicemicroservice.infrastructure.messaging;

import lombok.Data;

import java.math.BigDecimal;

@Data
class OrderPaidItem {
    private String name;
    private String description;
    private BigDecimal quantity;
    private BigDecimal price;
}
