package pl.batonikleonardo.invoicemicroservice.domain;

import java.time.Instant;
import java.util.List;

public class InvoiceCreatedEvent {
    private final Instant createdTime;
    private final InvoiceNumber invoiceNumber;
    private final InvoiceDate issuedDate;
    private final InvoiceDate paymentTerm;
    private final InvoiceInformation company;
    private final InvoiceInformation client;
    private final InvoiceItems invoiceItems;
    private final InvoiceSummary invoiceSummary;
    private final SourceOrderId sourceOrderId;

    private InvoiceCreatedEvent(Instant createdTime, InvoiceNumber invoiceNumber, InvoiceDate issuedDate, InvoiceDate paymentTerm, InvoiceInformation company, InvoiceInformation client, InvoiceItems invoiceItems, InvoiceSummary invoiceSummary, SourceOrderId sourceOrderId) {
        this.createdTime = createdTime;
        this.invoiceNumber = invoiceNumber;
        this.issuedDate = issuedDate;
        this.paymentTerm = paymentTerm;
        this.company = company;
        this.client = client;
        this.invoiceItems = invoiceItems;
        this.invoiceSummary = invoiceSummary;
        this.sourceOrderId = sourceOrderId;
    }

    static InvoiceCreatedEvent of(Invoice invoice) {
        final Instant createdTime = Instant.now();
        return new InvoiceCreatedEvent(createdTime, invoice.number(), invoice.issuedDate(), invoice.paymentTerm(),
                invoice.company(), invoice.client(), invoice.items(), invoice.summary(), invoice.sourceOrderId());
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public InvoiceNumber getInvoiceNumber() {
        return invoiceNumber;
    }

    public InvoiceDate getIssuedDate() {
        return issuedDate;
    }

    public InvoiceDate getPaymentTerm() {
        return paymentTerm;
    }

    public InvoiceInformation getCompany() {
        return company;
    }

    public InvoiceInformation getClient() {
        return client;
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems.asList();
    }

    public InvoiceSummary getInvoiceSummary() {
        return invoiceSummary;
    }

    public SourceOrderId getSourceOrderId() {
        return sourceOrderId;
    }

    public Long getSourceOrderIdValue() {
        return sourceOrderId.value();
    }

    public String getInvoiceNumberValue() {
        return invoiceNumber.value();
    }
}
