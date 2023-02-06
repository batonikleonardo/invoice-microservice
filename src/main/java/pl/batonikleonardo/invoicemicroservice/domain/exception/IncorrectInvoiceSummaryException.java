package pl.batonikleonardo.invoicemicroservice.domain.exception;

public class IncorrectInvoiceSummaryException extends Exception {
    public IncorrectInvoiceSummaryException(double taxValue) {
        super(String.format("Incorrect Invoice Summary tax value = %f", taxValue));
    }
}
