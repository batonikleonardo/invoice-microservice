package pl.batonikleonardo.invoicemicroservice.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pl.batonikleonardo.invoicemicroservice.application.OrderPaidEvent;
import pl.batonikleonardo.invoicemicroservice.application.OrderPaidEventHandler;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceFacade;
import pl.batonikleonardo.invoicemicroservice.domain.exception.*;

@Service
@Slf4j
class RabbitInvoiceCommandQueueListener extends OrderPaidEventHandler {

    RabbitInvoiceCommandQueueListener(InvoiceFacade invoiceFacade) {
        super(invoiceFacade);
    }

    @RabbitListener(queues = RabbitConfiguration.INVOICE_COMMAND_QUEUE)
    public void handle(String inputMessage) throws IncorrectSourceOrderIdException, InvoiceDataIsPastException,
            IncorrectInvoiceInformationPartException, IncorrectInvoiceItemException, IncorrectInvoiceSummaryException,
            InvoiceMissingOrIncorrectFieldException, JsonProcessingException {

        log.debug("Message read from myQueue : " + inputMessage);
        final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        final OrderPaid orderPaid = objectMapper.readValue(inputMessage, OrderPaid.class);

        log.debug("Parsed command : " + orderPaid.toString());
        OrderPaidEvent orderPaidEvent = OrderPaidEventMapper.map(orderPaid);
        handle(orderPaidEvent);
    }
}
