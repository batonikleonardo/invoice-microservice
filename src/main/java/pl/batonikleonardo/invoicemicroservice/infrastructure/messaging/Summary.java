package pl.batonikleonardo.invoicemicroservice.infrastructure.messaging;

import lombok.Data;

import java.math.BigDecimal;

@Data
class Summary {
    private double tax;
    private BigDecimal subtotal;
    private BigDecimal total;
}
