package pl.batonikleonardo.invoicemicroservice.domain.exception;

public class InvoiceMissingOrIncorrectFieldException extends Exception {
    public InvoiceMissingOrIncorrectFieldException(String fieldName) {
        super(String.format("Missing or incorrect invoice field = %s", fieldName));

    }
}
