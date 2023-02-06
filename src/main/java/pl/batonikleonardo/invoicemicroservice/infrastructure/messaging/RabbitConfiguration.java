package pl.batonikleonardo.invoicemicroservice.infrastructure.messaging;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RabbitConfiguration {

    static final String INVOICE_COMMAND_QUEUE = "queue.invoice.command";
    private static final String INVOICE_QUEUE = "queue.invoice";

    @Bean
    public Queue invoiceCommandQueue() {
        return new Queue(INVOICE_COMMAND_QUEUE, true);
    }
    @Bean
    public Queue invoiceQueue() {
        return new Queue(INVOICE_QUEUE, true);
    }
}
