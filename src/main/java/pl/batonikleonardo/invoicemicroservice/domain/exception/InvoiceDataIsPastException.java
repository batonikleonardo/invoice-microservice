package pl.batonikleonardo.invoicemicroservice.domain.exception;

import java.time.ZonedDateTime;

public class InvoiceDataIsPastException extends Exception {
    public InvoiceDataIsPastException(ZonedDateTime invoiceDateTime) {
        super(String.format("Entered invoice date = %s is past", invoiceDateTime));
    }
}
