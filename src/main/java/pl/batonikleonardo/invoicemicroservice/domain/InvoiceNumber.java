package pl.batonikleonardo.invoicemicroservice.domain;

import java.util.Objects;

final class InvoiceNumber {
    private final String value;

    InvoiceNumber(String value) throws IncorrectInvoiceNumberException {
        selfValidation(value);
        this.value = value;
    }

    String value() {
        return value;
    }

    private void selfValidation(String value) throws IncorrectInvoiceNumberException {
        if (Objects.isNull(value) || value.isBlank())
            throw new IncorrectInvoiceNumberException(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (InvoiceNumber) obj;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "InvoiceNumber[" +
                "invoiceNumber=" + value + ']';
    }


    static class IncorrectInvoiceNumberException extends Exception {
        private IncorrectInvoiceNumberException(String invoiceNumber) {
            super(String.format("Given invoice number = %s is incorrect", invoiceNumber));
        }
    }
}
