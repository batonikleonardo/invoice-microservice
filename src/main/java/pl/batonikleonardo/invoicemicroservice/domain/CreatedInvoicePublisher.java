package pl.batonikleonardo.invoicemicroservice.domain;

public interface CreatedInvoicePublisher {
    void publish(Invoice invoice);
}
