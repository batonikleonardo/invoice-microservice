package pl.batonikleonardo.invoicemicroservice.application;

import lombok.extern.slf4j.Slf4j;
import pl.batonikleonardo.invoicemicroservice.domain.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class OrderPaidEventHandler {

    private final InvoiceFacade invoiceFacade;

    protected OrderPaidEventHandler(InvoiceFacade invoiceFacade) {
        this.invoiceFacade = invoiceFacade;
    }

    void handle(OrderPaidEvent orderPaidEvent) throws InvoiceDate.InvoiceDataIsPastException, InvoiceInformation.IncorrectInvoiceInformationPartException, InvoiceItem.IncorrectInvoiceItemException, SourceOrderId.IncorrectSourceOrderIdException {
        log.info("Input order paid event = " + orderPaidEvent.toString());
        final InvoiceCreateCommand invoiceCreateCommand = map(orderPaidEvent);
        invoiceFacade.processInvoice(invoiceCreateCommand);
    }

    private InvoiceCreateCommand map(OrderPaidEvent orderPaidEvent) throws InvoiceDate.InvoiceDataIsPastException, SourceOrderId.IncorrectSourceOrderIdException, InvoiceInformation.IncorrectInvoiceInformationPartException, InvoiceItem.IncorrectInvoiceItemException {
        InvoiceDate issuedDate = new InvoiceDate(orderPaidEvent.paidDate());
        InvoiceInformation company = mapInvoiceInformation(orderPaidEvent.company());
        InvoiceInformation client = mapInvoiceInformation(orderPaidEvent.client());
        List<InvoiceItem> invoiceItems = mapItems(orderPaidEvent.items());
        SourceOrderId sourceOrderId = new SourceOrderId(orderPaidEvent.orderId());
        return new InvoiceCreateCommand(issuedDate, company, client, invoiceItems, sourceOrderId);
    }

    private InvoiceInformation mapInvoiceInformation(OrderPaidEventEntity orderPaidEventEntity) throws InvoiceInformation.IncorrectInvoiceInformationPartException {

        return new InvoiceInformation(
                orderPaidEventEntity.name(),
                orderPaidEventEntity.email(),
                orderPaidEventEntity.address(),
                orderPaidEventEntity.city(),
                orderPaidEventEntity.postalCode(),
                orderPaidEventEntity.province(),
                orderPaidEventEntity.country(),
                orderPaidEventEntity.taxIdentificationNumber()
        );
    }

    private List<InvoiceItem> mapItems(List<OrderItem> orderItemList) throws InvoiceItem.IncorrectInvoiceItemException {

        List<InvoiceItem> list = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            list.add(mapSingleItem(orderItem));
        }
        return list;

    }

    private InvoiceItem mapSingleItem(OrderItem orderItem) throws InvoiceItem.IncorrectInvoiceItemException {
        return new InvoiceItem(orderItem.name(), orderItem.description(), orderItem.quantity(), orderItem.price());
    }
}
