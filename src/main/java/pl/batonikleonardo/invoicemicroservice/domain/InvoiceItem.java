package pl.batonikleonardo.invoicemicroservice.domain;

import java.math.BigDecimal;
import java.util.Objects;

public final class InvoiceItem {
    private final String name;
    private final String description;
    private final BigDecimal quantity;
    private final BigDecimal price;

    public InvoiceItem(String name, String description, int quantity, BigDecimal price) throws IncorrectInvoiceItemException {
        selfValidation(name, quantity, price);
        this.name = name;
        this.description = description;
        this.quantity = new BigDecimal(quantity);
        this.price = price;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public int quantity() {
        return quantity.intValue();
    }

    BigDecimal price() {
        return price;
    }

    public BigDecimal totalPrice() {
        return price.multiply(quantity);
    }

    private void selfValidation(String name, int quantity, BigDecimal price) throws IncorrectInvoiceItemException {
        if (isDataNotValid(name, quantity, price)) {
            throw new IncorrectInvoiceItemException(name);
        }
    }

    private boolean isDataNotValid(String name, int quantity, BigDecimal price) {
        return Objects.nonNull(name)
                && Objects.nonNull(price)
                && !name.isBlank()
                && quantity < 0
                && price.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (InvoiceItem) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.description, that.description) &&
                Objects.equals(this.quantity, that.quantity) &&
                Objects.equals(this.price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, quantity, price);
    }

    @Override
    public String toString() {
        return "InvoiceItem[" +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "quantity=" + quantity + ", " +
                "price=" + price + ']';
    }


    public static class IncorrectInvoiceItemException extends Exception {
        private IncorrectInvoiceItemException(String itemName) {
            super(String.format("Incorrect item row data with name = %s", itemName));
        }
    }
}
