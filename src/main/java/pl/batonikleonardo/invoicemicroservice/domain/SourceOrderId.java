package pl.batonikleonardo.invoicemicroservice.domain;

import java.util.Objects;

public final class SourceOrderId {
    private final long value;

    public SourceOrderId(long value) throws IncorrectSourceOrderIdException {
        selfValidation(value);
        this.value = value;
    }

    long value() {
        return value;
    }

    private void selfValidation(long value) throws IncorrectSourceOrderIdException {
        if (value <= 0)
            throw new IncorrectSourceOrderIdException(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SourceOrderId that = (SourceOrderId) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "SourceOrderId{" +
                "value=" + value +
                '}';
    }

    public static class IncorrectSourceOrderIdException extends Exception {
        private IncorrectSourceOrderIdException(long value) {
            super(String.format("Given source order id = %d is incorrect", value));
        }
    }
}
