package pl.batonikleonardo.invoicemicroservice.infrastructure.messaging;

import lombok.Data;
import pl.batonikleonardo.invoicemicroservice.domain.Invoice;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceItem;

import java.time.ZonedDateTime;
import java.util.List;

@Data
class RabbitInvoiceCreatedEvent {
    private final String invoiceNumber;
    private final ZonedDateTime issuedDate;
    private final ZonedDateTime paymentTerm;
    private final Entity company;
    private final Entity client;
    private final List<Item> invoiceItems;
    private final Summary invoiceSummary;
    private final long sourceOrderId;

    static RabbitInvoiceCreatedEvent of(Invoice invoice) {
        return new RabbitInvoiceCreatedEvent(
                invoice.numberAsString(),
                invoice.issuedDateValue(),
                invoice.paymentTermValue(),
                mapCompany(invoice),
                mapClient(invoice),
                mapItems(invoice.invoiceItemsList()),
                mapSummary(invoice),
                invoice.sourceOrderIdValue()

        );
    }

    private static Entity mapCompany(Invoice invoice) {
        final Entity company = new Entity();

        company.setAddress(invoice.companyAddress());
        company.setCity(invoice.companyCity());
        company.setCountry(invoice.companyCountry());
        company.setEmail(invoice.companyEmail());
        company.setProvince(invoice.companyProvince());
        company.setPostalCode(invoice.companyPostalCode());
        company.setName(invoice.companyName());
        company.setTaxIdentificationNumber(invoice.companyTaxIdentificationNumber());

        return company;
    }

    private static Entity mapClient(Invoice invoice) {
        final Entity client = new Entity();

        client.setAddress(invoice.clientAddress());
        client.setCity(invoice.clientCity());
        client.setCountry(invoice.clientCountry());
        client.setEmail(invoice.clientEmail());
        client.setProvince(invoice.clientProvince());
        client.setPostalCode(invoice.clientPostalCode());
        client.setName(invoice.clientName());
        client.setTaxIdentificationNumber(invoice.clientTaxIdentificationNumber());

        return client;
    }

    private static List<Item> mapItems(List<InvoiceItem> invoiceItems) {
        return invoiceItems.stream()
                .map(RabbitInvoiceCreatedEvent::mapItem)
                .toList();
    }

    private static Item mapItem(InvoiceItem invoiceItem) {
        Item item = new Item();

        item.setName(invoiceItem.name());
        item.setDescription(invoiceItem.description());
        item.setQuantity(invoiceItem.quantity());
        item.setPrice(invoiceItem.totalPrice());

        return item;
    }

    private static Summary mapSummary(Invoice invoice) {
        Summary summary = new Summary();

        summary.setTax(invoice.summaryTaxValue());
        summary.setSubtotal(invoice.summarySubtotal());
        summary.setTotal(invoice.summaryTotal());

        return summary;
    }
}
