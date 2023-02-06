package pl.batonikleonardo.invoicemicroservice.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.batonikleonardo.invoicemicroservice.domain.CreatedInvoicePublisher;
import pl.batonikleonardo.invoicemicroservice.domain.Invoice;


@Slf4j
@Service
class CreatedInvoiceRabbitPublisher implements CreatedInvoicePublisher {

    private final RabbitTemplate rabbitTemplate;

    private final Queue queue;

    CreatedInvoiceRabbitPublisher(RabbitTemplate rabbitTemplate, @Qualifier("invoiceQueue") Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    @Override
    public void publish(Invoice invoice) {
        log.debug("Invoice to process : " + invoice.toString());

        try {
            RabbitInvoiceCreatedEvent invoiceCreatedEvent = RabbitInvoiceCreatedEvent.of(invoice);
            final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
            final String writeValueAsString = objectMapper.writeValueAsString(invoiceCreatedEvent);
            rabbitTemplate.convertAndSend(queue.getName(), writeValueAsString);
        } catch (Exception publishInvoiceCreatedEventException) {
            throw new PublishInvoiceCreatedEventException(publishInvoiceCreatedEventException);
        }

    }

    private static class PublishInvoiceCreatedEventException extends RuntimeException {
        public PublishInvoiceCreatedEventException(Exception exception) {
            super(exception);
        }
    }
}
