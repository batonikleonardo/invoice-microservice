package pl.batonikleonardo.invoicemicroservice.infrastructure.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.batonikleonardo.invoicemicroservice.domain.*;
import pl.batonikleonardo.invoicemicroservice.domain.exception.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InvoiceEventMongodbStorageTest {

    @Mock
    private InvoiceCreatedEventDocumentRepository invoiceCreatedEventDocumentRepository;

    @Mock
    private InvoiceNumberAssignedToOrderDocumentRepository invoiceNumberAssignedToOrderDocumentRepository;

    @InjectMocks
    private InvoiceEventMongodbStorage invoiceEventMongodbStorage;

    @Captor
    private ArgumentCaptor<InvoiceCreatedEventDocument> createdDocumentCaptor;

    @Captor
    private ArgumentCaptor<InvoiceNumberAssignedToOrderDocument> numberDocumentCaptor;

    @Test
    void InvoiceCreatedEventStoreTest() throws IncorrectSourceOrderIdException, IncorrectInvoiceInformationPartException, IncorrectInvoiceItemException, IncorrectInvoiceSummaryException, InvoiceMissingOrIncorrectFieldException {
        //Given
        final ZonedDateTime invoiceDateTime = ZonedDateTime.now().plusDays(4);
        final ZonedDateTime paymentDateTime = ZonedDateTime.now().plusDays(12);

        final InvoiceNumber inputInvoiceNumber = new InvoiceNumber("2023/02/04/5638");
        final InvoiceDate issuedDate = new InvoiceDate(invoiceDateTime);
        final InvoiceDate paymentTerm = new InvoiceDate(paymentDateTime);
        final SourceOrderId sourceOrderId = new SourceOrderId(300477);
        double taxValue = 10;

        final InvoiceInformation company = new InvoiceInformation("my company", "my.company@mail.test", "Street 1", "City 1",
                "88-345", "Province 1", "Country 1", "88665434324532");
        final InvoiceInformation client = new InvoiceInformation("drunk client", "d.client@mail.test", "Street 30", "City 3",
                "76-351", "Province 1", "Country 1", "886643234254");

        final InvoiceCreatedEventEntity exceptedClient = new InvoiceCreatedEventEntity("drunk client", "d.client@mail.test", "Street 30", "City 3",
                "76-351", "Province 1", "Country 1", "886643234254");
        final InvoiceCreatedEventEntity exceptedCompany = new InvoiceCreatedEventEntity("my company", "my.company@mail.test", "Street 1", "City 1",
                "88-345", "Province 1", "Country 1", "88665434324532");

        final BigDecimal brushPrice = BigDecimal.valueOf(125);
        final BigDecimal pastePrice = BigDecimal.valueOf(30);
        final BigDecimal keyPrice = BigDecimal.valueOf(2);

        final InvoiceItem invoiceItem1 = new InvoiceItem("Eye cleaning brush", null, 1, brushPrice);
        final InvoiceItem invoiceItem2 = new InvoiceItem("Eye paste with diamond abrasive", null, 4, pastePrice);
        final InvoiceItem invoiceItem3 = new InvoiceItem("Key to Yennefer's room", "Common Item", 2, keyPrice);

        final BigDecimal calculateInvoiceItemsSubtotalValue = BigDecimal.ZERO.add(brushPrice)
                .add(keyPrice)
                .add(keyPrice)
                .add(pastePrice)
                .add(pastePrice)
                .add(pastePrice)
                .add(pastePrice);

        final BigDecimal calculateInvoiceItemsTotalValue = calculateInvoiceItemsSubtotalValue
                .add(calculateInvoiceItemsSubtotalValue
                        .multiply(BigDecimal.valueOf(taxValue)
                                .movePointLeft(2))
                );

        final Invoice invoice = Invoice.builder()
                .invoiceNumber(inputInvoiceNumber)
                .issuedDate(issuedDate)
                .paymentTerm(paymentTerm)
                .company(company)
                .client(client)
                .invoiceItem(invoiceItem1)
                .invoiceItem(invoiceItem2)
                .invoiceItem(invoiceItem3)
                .sourceOrderId(sourceOrderId)
                .taxValue(taxValue)
                .build();
        //then

        final InvoiceCreatedEvent invoiceCreatedEvent = InvoiceCreatedEvent.of(invoice);

        //then
        invoiceEventMongodbStorage.store(invoiceCreatedEvent);

        verify(invoiceCreatedEventDocumentRepository).save(createdDocumentCaptor.capture());
        final InvoiceCreatedEventDocument createdEventDocument = createdDocumentCaptor.getValue();

        assertThat(createdEventDocument.getNumber()).isEqualTo("2023/02/04/5638");
        assertThat(createdEventDocument.getClient()).isEqualTo(exceptedClient);
        assertThat(createdEventDocument.getCompany()).isEqualTo(exceptedCompany);
        assertThat(createdEventDocument.getIssuedDate()).isEqualTo(invoiceDateTime.toLocalDateTime());
        assertThat(createdEventDocument.getPaymentTerm()).isEqualTo(paymentDateTime.toLocalDateTime());
        assertThat(createdEventDocument.getSummary().getTax()).isEqualTo(taxValue);
        assertThat(createdEventDocument.getSummary().getSubtotal()).isEqualTo(calculateInvoiceItemsSubtotalValue);
        assertThat(createdEventDocument.getSummary().getTotal()).isEqualTo(calculateInvoiceItemsTotalValue);

        assertThat(createdEventDocument.getItems()).hasSize(3)
                .extracting(InvoiceCreatedEventItem::getName,
                        InvoiceCreatedEventItem::getDescription,
                        InvoiceCreatedEventItem::getQuantity,
                        InvoiceCreatedEventItem::getPrice)
                .contains(
                        tuple("Eye cleaning brush", null, 1, brushPrice),
                        tuple("Eye paste with diamond abrasive", null, 4, pastePrice.add(pastePrice).add(pastePrice).add(pastePrice)),
                        tuple("Key to Yennefer's room", "Common Item", 2, keyPrice.add(keyPrice))
                );
    }

    @Test
    void test() throws IncorrectSourceOrderIdException {
        final InvoiceNumberAssignedToOrderEvent invoiceNumberAssignedToOrderEvent = new InvoiceNumberAssignedToOrderEvent(new SourceOrderId(3000), new InvoiceNumber("invoice/2345"));
        //then
        invoiceEventMongodbStorage.store(invoiceNumberAssignedToOrderEvent);
        verify(invoiceNumberAssignedToOrderDocumentRepository).save(numberDocumentCaptor.capture());


        final InvoiceNumberAssignedToOrderDocument assignedToOrderDocument = numberDocumentCaptor.getValue();
        assertThat(assignedToOrderDocument.getSourceOrderId()).isEqualTo(3000);
        assertThat(assignedToOrderDocument.getNumber()).isEqualTo("invoice/2345");
    }
}