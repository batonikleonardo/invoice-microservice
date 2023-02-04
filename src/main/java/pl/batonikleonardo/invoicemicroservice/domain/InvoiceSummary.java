package pl.batonikleonardo.invoicemicroservice.domain;

import java.math.BigDecimal;
import java.util.Objects;

final class InvoiceSummary {
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

    InvoiceItems invoiceItems() {
        return invoiceItems;
    }

    BigDecimal taxValue() {
        return taxValue;
    }

    public double taxValueAsDouble() {
        return taxValue.doubleValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (InvoiceSummary) obj;
        return Objects.equals(this.invoiceItems, that.invoiceItems) &&
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

    public BigDecimal subtotal() {
        return invoiceItems.totalPrice();
    }

    public BigDecimal total() {
        final BigDecimal calculatedTaxValue = invoiceItems.totalPrice().multiply(this.taxValue).movePointLeft(2);
        return invoiceItems.totalPrice().add(calculatedTaxValue);
    }


    static class IncorrectInvoiceSummaryException extends Exception {
        private IncorrectInvoiceSummaryException(double taxValue) {
        }
    }
}
