package pl.batonikleonardo.invoicemicroservice.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

final class InvoiceItems {
    private final List<InvoiceItem> itemList;

    InvoiceItems() {
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
}
