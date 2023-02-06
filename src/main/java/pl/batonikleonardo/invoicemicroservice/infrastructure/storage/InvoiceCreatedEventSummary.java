package pl.batonikleonardo.invoicemicroservice.infrastructure.storage;

import lombok.Data;

import java.math.BigDecimal;

@Data
class InvoiceCreatedEventSummary {
    private double tax;
    private BigDecimal subtotal;
    private BigDecimal total;
}
