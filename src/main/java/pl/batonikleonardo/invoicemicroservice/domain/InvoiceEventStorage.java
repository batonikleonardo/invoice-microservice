package pl.batonikleonardo.invoicemicroservice.domain;

public interface InvoiceEventStorage {
    void store(InvoiceCreatedEvent invoiceCreatedEvent);
}
