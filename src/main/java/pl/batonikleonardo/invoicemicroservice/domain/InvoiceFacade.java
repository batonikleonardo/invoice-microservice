package pl.batonikleonardo.invoicemicroservice.domain;

import lombok.extern.slf4j.Slf4j;
import pl.batonikleonardo.invoicemicroservice.domain.exception.IncorrectInvoiceSummaryException;
import pl.batonikleonardo.invoicemicroservice.domain.exception.InvoiceMissingOrIncorrectFieldException;

@Slf4j
public class InvoiceFacade {
    private final InvoiceNumberCalculator invoiceNumberCalculator;
    private final InvoicePaymentTermCalculator invoicePaymentTermCalculator;
    private final CreatedInvoicePublisher invoicePublisher;
    private final InvoiceEventStorage invoiceEventStorage;

    public InvoiceFacade(InvoiceNumberCalculator invoiceNumberCalculator, InvoicePaymentTermCalculator invoicePaymentTermCalculator, CreatedInvoicePublisher invoicePublisher, InvoiceEventStorage invoiceEventStorage) {
        this.invoiceNumberCalculator = invoiceNumberCalculator;
        this.invoicePaymentTermCalculator = invoicePaymentTermCalculator;
        this.invoicePublisher = invoicePublisher;
        this.invoiceEventStorage = invoiceEventStorage;
    }

    public void processInvoice(InvoiceCreateCommand invoiceCreateCommand) throws IncorrectInvoiceSummaryException, InvoiceMissingOrIncorrectFieldException {
        log.debug("Process invoice command = " + invoiceCreateCommand.toString());

        InvoiceNumber invoiceNumber = invoiceNumberCalculator.calculate(invoiceCreateCommand.issuedDate());
        final InvoiceNumberAssignedToOrderEvent invoiceNumberAssignedToOrderEvent
                = new InvoiceNumberAssignedToOrderEvent(invoiceCreateCommand.sourceOrderId(), invoiceNumber);
        invoiceEventStorage.store(invoiceNumberAssignedToOrderEvent);

        Invoice invoice = buildInvoiceFromCreateCommand(invoiceCreateCommand, invoiceNumber);

        InvoiceCreatedEvent invoiceCreatedEvent = InvoiceCreatedEvent.of(invoice);
        invoiceEventStorage.store(invoiceCreatedEvent);

        invoicePublisher.publish(invoice);
    }

    private Invoice buildInvoiceFromCreateCommand(InvoiceCreateCommand invoiceCreateCommand, InvoiceNumber invoiceNumber) throws IncorrectInvoiceSummaryException, InvoiceMissingOrIncorrectFieldException {
        InvoiceDate paymentTerm = invoicePaymentTermCalculator.calculate(invoiceCreateCommand.issuedDate());

        return Invoice.builder()
                .invoiceNumber(invoiceNumber)
                .issuedDate(invoiceCreateCommand.issuedDate())
                .paymentTerm(paymentTerm)
                .company(invoiceCreateCommand.company())
                .client(invoiceCreateCommand.client())
                .invoiceItems(invoiceCreateCommand.invoiceItems())
                .sourceOrderId(invoiceCreateCommand.sourceOrderId())
                .taxValue(invoiceCreateCommand.taxValue())
                .build();
    }
}
