package pl.batonikleonardo.invoicemicroservice.infrastructure.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pl.batonikleonardo.invoicemicroservice.application.OrderPaidEventHandler;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceFacade;
import pl.batonikleonardo.invoicemicroservice.domain.exception.*;

@Service
class RabbitInvoiceCommandQueueListener extends OrderPaidEventHandler {

    RabbitInvoiceCommandQueueListener(InvoiceFacade invoiceFacade) {
        super(invoiceFacade);
    }

    @RabbitListener(queues = RabbitConfiguration.INVOICE_COMMAND_QUEUE)
    public void handle(String inputMessage) throws IncorrectSourceOrderIdException, InvoiceDataIsPastException, IncorrectInvoiceInformationPartException, IncorrectInvoiceItemException, IncorrectInvoiceSummaryException, InvoiceMissingOrIncorrectFieldException {
        System.out.println("Message read from myQueue : " + inputMessage);

//        handle(orderPaidEvent);


    }
}
