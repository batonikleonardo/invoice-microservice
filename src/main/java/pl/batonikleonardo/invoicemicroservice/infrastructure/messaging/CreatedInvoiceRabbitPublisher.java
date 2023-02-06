package pl.batonikleonardo.invoicemicroservice.infrastructure.messaging;

import org.springframework.stereotype.Service;
import pl.batonikleonardo.invoicemicroservice.domain.CreatedInvoicePublisher;
import pl.batonikleonardo.invoicemicroservice.domain.Invoice;

@Service
class CreatedInvoiceRabbitPublisher implements CreatedInvoicePublisher {
    @Override
    public void publish(Invoice invoice) {

    }
}
