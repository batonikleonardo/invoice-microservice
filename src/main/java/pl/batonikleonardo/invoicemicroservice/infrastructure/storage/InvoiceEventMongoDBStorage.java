package pl.batonikleonardo.invoicemicroservice.infrastructure.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceCreatedEvent;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceEventStorage;

@Service
@RequiredArgsConstructor
class InvoiceEventMongoDBStorage implements InvoiceEventStorage {
    private final InvoiceEventRepository invoiceEventRepository;

    @Override
    public void store(InvoiceCreatedEvent invoiceCreatedEvent) {
        InvoiceCreatedEventDocument invoiceCreatedEventDocument = InvoiceCreatedEventDocumentMapper.map(invoiceCreatedEvent);
        invoiceEventRepository.save(invoiceCreatedEventDocument);
    }
}
