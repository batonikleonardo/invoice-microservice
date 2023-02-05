package pl.batonikleonardo.invoicemicroservice.domain;

import java.time.ZonedDateTime;
import java.util.Objects;

public final class InvoiceDate {
    private final ZonedDateTime invoiceDateTime;

    public InvoiceDate(ZonedDateTime invoiceDateTime) throws InvoiceDataIsPastException {
//        selfValidation(invoiceDateTime);
        this.invoiceDateTime = invoiceDateTime;
    }

    private void selfValidation(ZonedDateTime invoiceDateTime) throws InvoiceDataIsPastException {
        if (ZonedDateTime.now().isAfter(invoiceDateTime))
            throw new InvoiceDataIsPastException(invoiceDateTime);
    }

    ZonedDateTime invoiceDateTime() {
        return invoiceDateTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (InvoiceDate) obj;
        return Objects.equals(this.invoiceDateTime, that.invoiceDateTime);
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


    public static class InvoiceDataIsPastException extends Exception {
        private InvoiceDataIsPastException(ZonedDateTime invoiceDateTime) {
            super(String.format("Entered invoice date = %s is past", invoiceDateTime));
        }
    }
}
