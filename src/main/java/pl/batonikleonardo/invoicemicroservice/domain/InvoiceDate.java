package pl.batonikleonardo.invoicemicroservice.domain;

import pl.batonikleonardo.invoicemicroservice.domain.exception.InvoiceDataIsPastException;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public final class InvoiceDate {
    private final ZonedDateTime invoiceDateTime;

    public InvoiceDate(ZonedDateTime invoiceDateTime) {
//        selfValidation(invoiceDateTime);
        this.invoiceDateTime = invoiceDateTime;
    }

    public InvoiceDate plus(int days) {
        return new InvoiceDate(invoiceDateTime.plusDays(days));
    }

    ZonedDateTime invoiceDateTime() {
        return invoiceDateTime;
    }

    public String asSlashSequenceWithUUID(UUID randomUUID) {
        return invoiceDateTime.toString() + "/" + randomUUID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (InvoiceDate) obj;
        return Objects.equals(this.invoiceDateTime, that.invoiceDateTime);
    }

    private void selfValidation(ZonedDateTime invoiceDateTime) throws InvoiceDataIsPastException {
        if (ZonedDateTime.now().isAfter(invoiceDateTime))
            throw new InvoiceDataIsPastException(invoiceDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceDateTime);
    }

    @Override
    public String toString() {
        return "InvoiceDate[" +
                "invoiceDateTime=" + invoiceDateTime + ']';
    }

    public ZonedDateTime value() {
        return invoiceDateTime;
    }
}
