package pl.batonikleonardo.invoicemicroservice.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
class RabbitInvoiceCommandQueueListenerTest {
    @Mock
    private InvoiceFacade invoiceFacade;
    @InjectMocks
    private RabbitInvoiceCommandQueueListener rabbitInvoiceCommandQueueListener;


    @Test
    void whenCorrectMessageThenHandledCorrectCommand() throws InvoiceDataIsPastException, IncorrectInvoiceInformationPartException, IncorrectInvoiceItemException, IncorrectSourceOrderIdException, IncorrectInvoiceSummaryException, InvoiceMissingOrIncorrectFieldException, JsonProcessingException {
        //given
        final String inputMessage = """
                {
                  "orderId": 100,
                  "taxValue": 20,
                  "date": "2023-02-06T10:18:47.723Z",
                  "company": {
                    "name": "company name",
                    "email": "comapany.email@mail.test",
                    "address": "address1",
                    "city": "city",
                    "postalCode": "56-900",
                    "province": "province1",
                    "country": "country1",
                    "taxIdentificationNumber": "34457676787686867"
                  },
                  "client": {
                    "name": "client name",
                    "email": "cient.email@mail.test",
                    "address": "address2",
                    "city": "city2",
                    "postalCode": "54-900",
                    "province": "province10",
                    "country": "country1",
                    "taxIdentificationNumber": "344576767876812332"
                  },
                  "items": [
                    {
                      "name": "Eye cleaning brush",
                      "quantity": 1,
                      "price": "125"
                    },
                    {
                      "name": "Eye paste with diamond abrasive",
                      "quantity": 4,
                      "price": "120"
                    },
                    {
                      "name": "Key to Yennefer's room",
                      "description": "Common Item",
                      "quantity": 2,
                      "price": "4"
                    }
                  ]
                }
                """;

        final int orderId = 100;
        final ZonedDateTime paidDate = ZonedDateTime.now();
        final SourceOrderId orderIdAsSourceOrderId = new SourceOrderId(orderId);
        final InvoiceDate paidDateAsInvoiceDate = new InvoiceDate(paidDate);


        final InvoiceInformation invoiceCompany = new InvoiceInformation("company name", "comapany.email@mail.test", "address1", "city", "56-900", "province1", "country1", "34457676787686867");
        final InvoiceInformation invoiceClient = new InvoiceInformation("client name", "cient.email@mail.test", "address2", "city2", "54-900", "province10", "country1", "344576767876812332");

        final BigDecimal brushPrice = BigDecimal.valueOf(125);
        final BigDecimal pastePrice = BigDecimal.valueOf(120);
        final BigDecimal keyPrice = BigDecimal.valueOf(4);

        //then
        rabbitInvoiceCommandQueueListener.handle(inputMessage);

        ArgumentCaptor<InvoiceCreateCommand> argumentCaptor = ArgumentCaptor.forClass(InvoiceCreateCommand.class);
        verify(invoiceFacade).processInvoice(argumentCaptor.capture());

        InvoiceCreateCommand invoiceCreateCommand = argumentCaptor.getValue();

        assertThat(invoiceCreateCommand).isNotNull();
        assertThat(invoiceCreateCommand.sourceOrderId()).isEqualTo(orderIdAsSourceOrderId);
//        assertThat(invoiceCreateCommand.issuedDate()).isEqualTo(paidDateAsInvoiceDate);
        assertThat(invoiceCreateCommand.company()).isEqualTo(invoiceCompany);
        assertThat(invoiceCreateCommand.client()).isEqualTo(invoiceClient);
        assertThat(invoiceCreateCommand.taxValue()).isEqualTo(20);

        assertThat(invoiceCreateCommand.invoiceItems()).hasSize(3)
                .extracting(InvoiceItem::name, InvoiceItem::description, InvoiceItem::quantity, InvoiceItem::totalPrice)
                .contains(
                        tuple("Eye cleaning brush", null, 1, brushPrice),
                        tuple("Eye paste with diamond abrasive", null, 4, pastePrice.add(pastePrice).add(pastePrice).add(pastePrice)),
                        tuple("Key to Yennefer's room", "Common Item", 2, keyPrice.add(keyPrice))
                );

    }
}
