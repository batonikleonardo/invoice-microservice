package pl.batonikleonardo.invoicemicroservice.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.batonikleonardo.invoicemicroservice.domain.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderPaidEventHandlerTest {

    @Mock
    private InvoiceFacade invoiceFacade;
    @Captor
    private ArgumentCaptor<InvoiceCreateCommand> invoiceCreateCommandArgumentCaptor;

    @Test
    void handle() throws InvoiceDate.InvoiceDataIsPastException, InvoiceInformation.IncorrectInvoiceInformationPartException, InvoiceItem.IncorrectInvoiceItemException, SourceOrderId.IncorrectSourceOrderIdException {
        //given
        final OrderPaidEventHandler orderPaidEventHandler = new OrderPaidEventHandler(invoiceFacade) {
            @Override
            void handle(OrderPaidEvent orderPaidEvent) throws InvoiceDate.InvoiceDataIsPastException, InvoiceInformation.IncorrectInvoiceInformationPartException, InvoiceItem.IncorrectInvoiceItemException, SourceOrderId.IncorrectSourceOrderIdException {
                super.handle(orderPaidEvent);
            }
        };

        final int orderId = 100;
        final ZonedDateTime paidDate = ZonedDateTime.now();
        final SourceOrderId orderIdAsSourceOrderId = new SourceOrderId(orderId);
        final InvoiceDate paidDateAsInvoiceDate = new InvoiceDate(paidDate);

        final OrderPaidEventAddress companyAddress = new OrderPaidEventAddress("address1", "city", "56-900", "province1", "country1");
        final OrderPaidEventAddress clientAddress = new OrderPaidEventAddress("address2", "city2", "54-900", "province10", "country1");

        final OrderPaidEventEntity company = new OrderPaidEventEntity("company name", "comapany.email@mail.test", "34457676787686867", companyAddress);
        final OrderPaidEventEntity client = new OrderPaidEventEntity("client name", "cient.email@mail.test", "344576767876812332", clientAddress);

        final InvoiceInformation invoiceCompany = new InvoiceInformation("company name", "comapany.email@mail.test", "address1", "city", "56-900", "province1", "country1", "34457676787686867");
        final InvoiceInformation invoiceClient = new InvoiceInformation("client name", "cient.email@mail.test", "address2", "city2", "54-900", "province10", "country1","344576767876812332");

        final BigDecimal brushPrice = BigDecimal.valueOf(125);
        final BigDecimal pastePrice = BigDecimal.valueOf(30);
        final BigDecimal keyPrice = BigDecimal.valueOf(2);

        final OrderItem orderItem1 = new OrderItem("Eye cleaning brush", null, 1, brushPrice);
        final OrderItem orderItem2 = new OrderItem("Eye paste with diamond abrasive", null, 4, pastePrice);
        final OrderItem orderItem3 = new OrderItem("Key to Yennefer's room", "Common Item", 2, keyPrice);

        final List<OrderItem> orderItems = List.of(orderItem1, orderItem2, orderItem3);

        final OrderPaidEvent orderPaidEvent = new OrderPaidEvent(orderId, paidDate, company, client, orderItems);

        //when
        doNothing().when(invoiceFacade).processInvoice(any(InvoiceCreateCommand.class));
        //then
        orderPaidEventHandler.handle(orderPaidEvent);

        verify(invoiceFacade).processInvoice(invoiceCreateCommandArgumentCaptor.capture());

        InvoiceCreateCommand invoiceCreateCommand = invoiceCreateCommandArgumentCaptor.getValue();

        assertThat(invoiceCreateCommand).isNotNull();
        assertThat(invoiceCreateCommand.sourceOrderId()).isEqualTo(orderIdAsSourceOrderId);
        assertThat(invoiceCreateCommand.issuedDate()).isEqualTo(paidDateAsInvoiceDate);
        assertThat(invoiceCreateCommand.company()).isEqualTo(invoiceCompany);
        assertThat(invoiceCreateCommand.client()).isEqualTo(invoiceClient);

        assertThat(invoiceCreateCommand.invoiceItems()).hasSize(3)
                .extracting(InvoiceItem::name, InvoiceItem::description, InvoiceItem::quantity, InvoiceItem::totalPrice)
                .contains(
                        tuple("Eye cleaning brush", null, 1, brushPrice),
                        tuple("Eye paste with diamond abrasive", null, 4, pastePrice.add(pastePrice).add(pastePrice).add(pastePrice)),
                        tuple("Key to Yennefer's room", "Common Item", 2, keyPrice.add(keyPrice))
                );
    }

}