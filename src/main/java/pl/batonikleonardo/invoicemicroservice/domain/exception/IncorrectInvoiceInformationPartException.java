package pl.batonikleonardo.invoicemicroservice.domain.exception;

public class IncorrectInvoiceInformationPartException extends Exception {
    public IncorrectInvoiceInformationPartException(String name, String value) {
        super(String.format("Incorrect invoice information : %s = %s", name, value));
    }
}
