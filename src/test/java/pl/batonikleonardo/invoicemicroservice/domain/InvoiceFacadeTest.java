package pl.batonikleonardo.invoicemicroservice.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.batonikleonardo.invoicemicroservice.domain.exception.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvoiceFacadeTest {

    @Mock
    private InvoiceNumberCalculator invoiceNumberCalculator;
    @Mock
    private InvoicePaymentTermCalculator invoicePaymentTermCalculator;
    @Mock
    private CreatedInvoicePublisher invoicePublisher;
    @Mock
    private InvoiceEventStorage invoiceEventStorage;

    @InjectMocks
    private InvoiceFacade invoiceFacade;

    @Test
    void whenProcessInvoiceTermAndNumberCalculatedCorrectly() throws IncorrectInvoiceSummaryException, InvoiceMissingOrIncorrectFieldException, IncorrectSourceOrderIdException, IncorrectInvoiceInformationPartException, IncorrectInvoiceItemException {
        //Given
        final ZonedDateTime invoiceDateTime = ZonedDateTime.now().plusDays(4);
        final ZonedDateTime paymentDateTime = ZonedDateTime.now().plusDays(12);

        final InvoiceNumber invoiceNumber = new InvoiceNumber("2023/02/04/5638");
        final InvoiceDate issuedDate = new InvoiceDate(invoiceDateTime);
        final InvoiceDate paymentTerm = new InvoiceDate(paymentDateTime);
        final SourceOrderId sourceOrderId = new SourceOrderId(300477);
        double taxValue = 45;

        final InvoiceInformation company = new InvoiceInformation("my company", "my.company@mail.test", "Street 1", "City 1",
                "88-345", "Province 1", "Country 1", "88665434324532");
        final InvoiceInformation client = new InvoiceInformation("drunk client", "d.client@mail.test", "Street 30", "City 3",
                "76-351", "Province 1", "Country 1", "886643234254");

        final BigDecimal brushPrice = BigDecimal.valueOf(125);
        final BigDecimal pastePrice = BigDecimal.valueOf(30);
        final BigDecimal keyPrice = BigDecimal.valueOf(2);

        final InvoiceItem invoiceItem1 = new InvoiceItem("Eye cleaning brush", null, 1, brushPrice);
        final InvoiceItem invoiceItem2 = new InvoiceItem("Eye paste with diamond abrasive", null, 4, pastePrice);
        final InvoiceItem invoiceItem3 = new InvoiceItem("Key to Yennefer's room", "Common Item", 2, keyPrice);

        final List<InvoiceItem> invoiceItems = List.of(invoiceItem1, invoiceItem2, invoiceItem3);

        final InvoiceCreateCommand invoiceCreateCommand = new InvoiceCreateCommand(issuedDate, company, client, invoiceItems, taxValue, sourceOrderId);
        //when
        when(invoiceNumberCalculator.calculate(issuedDate)).thenReturn(invoiceNumber);
        when(invoicePaymentTermCalculator.calculate(issuedDate)).thenReturn(paymentTerm);
        //then
        invoiceFacade.processInvoice(invoiceCreateCommand);

        verify(invoiceNumberCalculator).calculate(issuedDate);
        verify(invoicePaymentTermCalculator).calculate(issuedDate);
        ArgumentCaptor<Invoice> invoiceArgumentCaptor = ArgumentCaptor.forClass(Invoice.class);
        verify(invoicePublisher).publish(invoiceArgumentCaptor.capture());

        final Invoice resultInvoice = invoiceArgumentCaptor.getValue();
        assertThat(resultInvoice.number()).isEqualTo(invoiceNumber);
        assertThat(resultInvoice.paymentTerm()).isEqualTo(paymentTerm);
    }

    @Test
    void whenProcessInvoiceIsBuiltCorrectly() throws IncorrectInvoiceSummaryException, InvoiceMissingOrIncorrectFieldException, IncorrectSourceOrderIdException, IncorrectInvoiceInformationPartException, IncorrectInvoiceItemException {
        //Given
        final ZonedDateTime invoiceDateTime = ZonedDateTime.now().plusDays(4);
        final ZonedDateTime paymentDateTime = ZonedDateTime.now().plusDays(12);

        final InvoiceNumber invoiceNumber = new InvoiceNumber("2023/02/04/5638");
        final InvoiceDate issuedDate = new InvoiceDate(invoiceDateTime);
        final InvoiceDate paymentTerm = new InvoiceDate(paymentDateTime);
        final SourceOrderId sourceOrderId = new SourceOrderId(300477);
        double taxValue = 10;

        final InvoiceInformation company = new InvoiceInformation("my company", "my.company@mail.test", "Street 1", "City 1",
                "88-345", "Province 1", "Country 1", "88665434324532");
        final InvoiceInformation client = new InvoiceInformation("drunk client", "d.client@mail.test", "Street 30", "City 3",
                "76-351", "Province 1", "Country 1", "886643234254");

        final BigDecimal brushPrice = BigDecimal.valueOf(125);
        final BigDecimal pastePrice = BigDecimal.valueOf(30);
        final BigDecimal keyPrice = BigDecimal.valueOf(2);

        final InvoiceItem invoiceItem1 = new InvoiceItem("Eye cleaning brush", null, 1, brushPrice);
        final InvoiceItem invoiceItem2 = new InvoiceItem("Eye paste with diamond abrasive", null, 4, pastePrice);
        final InvoiceItem invoiceItem3 = new InvoiceItem("Key to Yennefer's room", "Common Item", 2, keyPrice);

        final Invoice invoice = Invoice.builder()
                .invoiceNumber(invoiceNumber)
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
        final List<InvoiceItem> invoiceItems = List.of(invoiceItem1, invoiceItem2, invoiceItem3);

        final InvoiceCreateCommand invoiceCreateCommand = new InvoiceCreateCommand(issuedDate, company, client, invoiceItems, taxValue, sourceOrderId);

        //when
        when(invoiceNumberCalculator.calculate(issuedDate)).thenReturn(invoiceNumber);
        when(invoicePaymentTermCalculator.calculate(issuedDate)).thenReturn(paymentTerm);
        //then
        invoiceFacade.processInvoice(invoiceCreateCommand);

        ArgumentCaptor<Invoice> invoiceArgumentCaptor = ArgumentCaptor.forClass(Invoice.class);
        verify(invoicePublisher).publish(invoiceArgumentCaptor.capture());

        final Invoice resultInvoice = invoiceArgumentCaptor.getValue();
        assertThat(resultInvoice).isEqualTo(invoice);
    }

    @Test
    void whenProcessInvoiceInvoiceCreatedEventBuiltCorrectly() throws IncorrectInvoiceSummaryException, InvoiceMissingOrIncorrectFieldException, IncorrectSourceOrderIdException, IncorrectInvoiceInformationPartException, IncorrectInvoiceItemException {
        //Given
        final ZonedDateTime invoiceDateTime = ZonedDateTime.now().plusDays(4);
        final ZonedDateTime paymentDateTime = ZonedDateTime.now().plusDays(12);

        final InvoiceNumber invoiceNumber = new InvoiceNumber("2023/02/04/5638");
        final InvoiceDate issuedDate = new InvoiceDate(invoiceDateTime);
        final InvoiceDate paymentTerm = new InvoiceDate(paymentDateTime);
        final SourceOrderId sourceOrderId = new SourceOrderId(300477);
        double taxValue = 10;

        final InvoiceInformation company = new InvoiceInformation("my company", "my.company@mail.test", "Street 1", "City 1",
                "88-345", "Province 1", "Country 1", "88665434324532");
        final InvoiceInformation client = new InvoiceInformation("drunk client", "d.client@mail.test", "Street 30", "City 3",
                "76-351", "Province 1", "Country 1", "886643234254");

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

        final List<InvoiceItem> invoiceItems = List.of(invoiceItem1, invoiceItem2, invoiceItem3);

        final InvoiceCreateCommand invoiceCreateCommand = new InvoiceCreateCommand(issuedDate, company, client, invoiceItems, taxValue, sourceOrderId);

        //when
        when(invoiceNumberCalculator.calculate(issuedDate)).thenReturn(invoiceNumber);
        when(invoicePaymentTermCalculator.calculate(issuedDate)).thenReturn(paymentTerm);
        //then
        invoiceFacade.processInvoice(invoiceCreateCommand);

        ArgumentCaptor<InvoiceCreatedEvent> invoiceCreatedEventArgumentCaptor = ArgumentCaptor.forClass(InvoiceCreatedEvent.class);
        verify(invoiceEventStorage).store(invoiceCreatedEventArgumentCaptor.capture());

        final InvoiceCreatedEvent invoiceCreatedEventResult = invoiceCreatedEventArgumentCaptor.getValue();
        assertThat(invoiceCreatedEventResult.getCreatedTime()).isBefore(Instant.now());
        assertThat(invoiceCreatedEventResult.getInvoiceNumber()).isEqualTo(invoiceNumber);
        assertThat(invoiceCreatedEventResult.getIssuedDate()).isEqualTo(issuedDate);
        assertThat(invoiceCreatedEventResult.getPaymentTerm()).isEqualTo(paymentTerm);
        assertThat(invoiceCreatedEventResult.getClient()).isEqualTo(client);
        assertThat(invoiceCreatedEventResult.getCompany()).isEqualTo(company);
        assertThat(invoiceCreatedEventResult.getInvoiceSummary().subtotal()).isEqualTo(calculateInvoiceItemsSubtotalValue);
        assertThat(invoiceCreatedEventResult.getInvoiceSummary().total()).isEqualTo(calculateInvoiceItemsTotalValue);
        assertThat(invoiceCreatedEventResult.getInvoiceItems()).hasSize(3).contains(invoiceItem1, invoiceItem2, invoiceItem3);
    }
}