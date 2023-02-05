package pl.batonikleonardo.invoicemicroservice.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class InvoiceItems {
    private final List<InvoiceItem> itemList;

    public InvoiceItems() {
        this.itemList = new ArrayList<>();
    }

    void add(InvoiceItem invoiceItem) {
        itemList.add(invoiceItem);
    }

    BigDecimal totalPrice() {
        return itemList.stream()
                .map(InvoiceItem::totalPrice)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }

    boolean isEmpty() {
        return itemList.isEmpty();
    }

    public List<InvoiceItem> asList() {
        return itemList;
    }

    public void addAll(List<InvoiceItem> invoiceItems) {
        this.itemList.addAll(invoiceItems);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceItems that)) return false;
        return Objects.equals(itemList, that.itemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemList);
    }

    @Override
    public String toString() {
        return "InvoiceItems{" +
                "itemList=" + itemList +
                '}';
    }
}
