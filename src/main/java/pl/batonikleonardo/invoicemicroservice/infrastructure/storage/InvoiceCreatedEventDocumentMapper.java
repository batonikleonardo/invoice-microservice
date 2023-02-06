package pl.batonikleonardo.invoicemicroservice.infrastructure.storage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceCreatedEvent;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceInformation;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceItem;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceSummary;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class InvoiceCreatedEventDocumentMapper {
    static InvoiceCreatedEventDocument map(InvoiceCreatedEvent invoiceCreatedEvent) {
        InvoiceCreatedEventDocument invoiceCreatedEventDocument = new InvoiceCreatedEventDocument();

        invoiceCreatedEventDocument.setNumber(invoiceCreatedEvent.getInvoiceNumberValue());
        invoiceCreatedEventDocument.setCreatedTime(invoiceCreatedEvent.getCreatedTime());
        //Added bug to fix later, missing zone data
        invoiceCreatedEventDocument.setPaymentTerm(invoiceCreatedEvent.getPaymentTerm().value().toLocalDateTime());
        invoiceCreatedEventDocument.setIssuedDate(invoiceCreatedEvent.getIssuedDate().value().toLocalDateTime());

        invoiceCreatedEventDocument.setClient(mapEntity(invoiceCreatedEvent.getClient()));
        invoiceCreatedEventDocument.setCompany(mapEntity(invoiceCreatedEvent.getCompany()));
        invoiceCreatedEventDocument.setItems(mapItems(invoiceCreatedEvent.getInvoiceItems()));
        invoiceCreatedEventDocument.setSummary(mapSummary(invoiceCreatedEvent.getInvoiceSummary()));

        return invoiceCreatedEventDocument;
    }

    private static InvoiceCreatedEventEntity mapEntity(InvoiceInformation invoiceInformation) {
        final InvoiceCreatedEventEntity invoiceCreatedEventEntity = new InvoiceCreatedEventEntity();
        invoiceCreatedEventEntity.setAddress(invoiceInformation.address());
        invoiceCreatedEventEntity.setCity(invoiceInformation.city());
        invoiceCreatedEventEntity.setCountry(invoiceInformation.country());
        invoiceCreatedEventEntity.setEmail(invoiceInformation.email());
        invoiceCreatedEventEntity.setProvince(invoiceInformation.province());
        invoiceCreatedEventEntity.setPostalCode(invoiceInformation.postalCode());
        invoiceCreatedEventEntity.setName(invoiceInformation.name());
        invoiceCreatedEventEntity.setTaxIdentificationNumber(invoiceInformation.taxIdentificationNumber());
        return invoiceCreatedEventEntity;
    }

    private static InvoiceCreatedEventSummary mapSummary(InvoiceSummary invoiceSummary) {
        final InvoiceCreatedEventSummary invoiceCreatedEventSummary = new InvoiceCreatedEventSummary();

        invoiceCreatedEventSummary.setTax(invoiceSummary.taxValueAsDouble());
        invoiceCreatedEventSummary.setSubtotal(invoiceSummary.subtotal());
        invoiceCreatedEventSummary.setTotal(invoiceSummary.total());

        return invoiceCreatedEventSummary;
    }

    private static List<InvoiceCreatedEventItem> mapItems(List<InvoiceItem> invoiceItems) {
        return invoiceItems.stream()
                .map(InvoiceCreatedEventDocumentMapper::mapItem)
                .toList();
    }

    private static InvoiceCreatedEventItem mapItem(InvoiceItem invoiceItem) {
        InvoiceCreatedEventItem invoiceCreatedEventItem = new InvoiceCreatedEventItem();

        invoiceCreatedEventItem.setName(invoiceItem.name());
        invoiceCreatedEventItem.setPrice(invoiceItem.totalPrice());
        invoiceCreatedEventItem.setDescription(invoiceItem.description());
        invoiceCreatedEventItem.setQuantity(invoiceItem.quantity());

        return invoiceCreatedEventItem;
    }
}
