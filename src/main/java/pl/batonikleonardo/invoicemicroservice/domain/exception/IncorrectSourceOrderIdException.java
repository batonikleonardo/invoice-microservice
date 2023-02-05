package pl.batonikleonardo.invoicemicroservice.domain.exception;

public class IncorrectSourceOrderIdException extends Exception {
    public IncorrectSourceOrderIdException(long value) {
        super(String.format("Given source order id = %d is incorrect", value));
    }
}
