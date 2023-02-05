package pl.batonikleonardo.invoicemicroservice.domain.exception;

public class IncorrectInvoiceItemException extends Exception {
    public IncorrectInvoiceItemException(String itemName) {
        super(String.format("Incorrect item row data with name = %s", itemName));
    }
}
