package pl.batonikleonardo.invoicemicroservice.domain;

import java.util.List;

public record InvoiceCreateCommand(InvoiceDate issuedDate, InvoiceInformation company, InvoiceInformation client,
                                   List<InvoiceItem> invoiceItems, SourceOrderId sourceOrderId) {
}

