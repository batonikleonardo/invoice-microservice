package pl.batonikleonardo.invoicemicroservice.infrastructure.storage;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
class InvoiceCreatedEventDocument {
    @Id
    private String number;
    private Instant createdTime;
    private LocalDateTime issuedDate;
    private LocalDateTime paymentTerm;
    private InvoiceCreatedEventEntity company;
    private InvoiceCreatedEventEntity client;
    private List<InvoiceCreatedEventItem> items;
    private InvoiceCreatedEventSummary summary;
    private Long sourceOrderId;
}

