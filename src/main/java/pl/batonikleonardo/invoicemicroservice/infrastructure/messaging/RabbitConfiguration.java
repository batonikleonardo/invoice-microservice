package pl.batonikleonardo.invoicemicroservice.infrastructure.messaging;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RabbitConfiguration {

    public static final String INVOICE_COMMAND_QUEUE = "queue.invoice.command";

    @Bean
    public Queue myQueue() {
        return new Queue(INVOICE_COMMAND_QUEUE, true);
    }
}
