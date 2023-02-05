package pl.batonikleonardo.invoicemicroservice.domain.exception;

public class IncorrectInvoiceSummaryException extends Exception {
    public IncorrectInvoiceSummaryException(double taxValue) {
    }
}
