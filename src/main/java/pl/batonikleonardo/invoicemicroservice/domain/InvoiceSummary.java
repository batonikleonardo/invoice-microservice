package pl.batonikleonardo.invoicemicroservice.domain;

import pl.batonikleonardo.invoicemicroservice.domain.exception.IncorrectInvoiceSummaryException;

import java.math.BigDecimal;
import java.util.Objects;

public final class InvoiceSummary {
    private final InvoiceItems invoiceItems;
    private final BigDecimal taxValue;

    InvoiceSummary(InvoiceItems invoiceItems, double taxValue) throws IncorrectInvoiceSummaryException {
        selfValidation(invoiceItems, taxValue);
        this.invoiceItems = invoiceItems;
        this.taxValue = BigDecimal.valueOf(taxValue);
    }

    private void selfValidation(InvoiceItems invoiceItems, double taxValue) throws IncorrectInvoiceSummaryException {
        if (isDataIsNotValid(invoiceItems, taxValue)) {
            throw new IncorrectInvoiceSummaryException(taxValue);
        }
    }

    private boolean isDataIsNotValid(InvoiceItems invoiceItems, double taxValue) {
        return invoiceItems.isEmpty() && taxValue <= 0;
    }

    BigDecimal taxValue() {
        return taxValue;
    }

    public double taxValueAsDouble() {
        return taxValue.doubleValue();
    }

    public BigDecimal subtotal() {
        return invoiceItems.totalPrice();
    }

    public BigDecimal total() {
        final BigDecimal calculatedTaxValue = invoiceItems.totalPrice().multiply(this.taxValue).movePointLeft(2);
        return invoiceItems.totalPrice().add(calculatedTaxValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (InvoiceSummary) obj;
        return Objects.equals(this.subtotal(), that.subtotal()) &&
                Objects.equals(this.taxValue, that.taxValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceItems, taxValue);
    }

    @Override
    public String toString() {
        return "InvoiceSummary[" +
                "invoiceItems=" + invoiceItems + ", " +
                "taxValue=" + taxValue + ']';
    }
}
