package pl.batonikleonardo.invoicemicroservice.domain.exception;

public class IncorrectInvoiceNumberException extends Exception {
    public IncorrectInvoiceNumberException(String invoiceNumber) {
        super(String.format("Given invoice number = %s is incorrect", invoiceNumber));
    }
}
