package pl.batonikleonardo.invoicemicroservice.domain;

import java.util.Objects;

public final class InvoiceNumber {
    private final String value;

    public InvoiceNumber(String value) {
        this.value = value;
    }

    String value() {
        return value;
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


}
