package pl.batonikleonardo.invoicemicroservice.infrastructure.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceCreatedEvent;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceEventStorage;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceNumberAssignedToOrderEvent;

@Service
@RequiredArgsConstructor
class InvoiceEventMongodbStorage implements InvoiceEventStorage {
    private final InvoiceCreatedEventDocumentRepository invoiceCreatedEventDocumentRepository;
    private final InvoiceNumberAssignedToOrderDocumentRepository invoiceNumberAssignedToOrderDocumentRepository;

    @Override
    public void store(InvoiceCreatedEvent invoiceCreatedEvent) {
        InvoiceCreatedEventDocument invoiceCreatedEventDocument = InvoiceCreatedEventDocumentMapper.map(invoiceCreatedEvent);
        invoiceCreatedEventDocumentRepository.save(invoiceCreatedEventDocument);
    }

    @Override
    public void store(InvoiceNumberAssignedToOrderEvent invoiceNumberAssignedToOrderEvent) {
        final long sourceOrderIdValue = invoiceNumberAssignedToOrderEvent.sourceOrderIdValue();
        final String invoiceNumberValue = invoiceNumberAssignedToOrderEvent.invoiceNumberValue();

        final InvoiceNumberAssignedToOrderDocument invoiceNumberAssignedToOrderDocument
                = new InvoiceNumberAssignedToOrderDocument(sourceOrderIdValue, invoiceNumberValue);
        invoiceNumberAssignedToOrderDocumentRepository.save(invoiceNumberAssignedToOrderDocument);

    }
}
