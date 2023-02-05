package pl.batonikleonardo.invoicemicroservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.batonikleonardo.invoicemicroservice.domain.*;

@SpringBootApplication
@Slf4j
public class InvoiceMicroserviceApplication {

    public static void main(String[] args) {
        log.info("InvoiceMicroserviceApplication Starting");
        SpringApplication.run(InvoiceMicroserviceApplication.class, args);
    }

    @Bean
    InvoiceFacade invoiceFacade(InvoiceNumberCalculator invoiceNumberCalculator, InvoicePaymentTermCalculator invoicePaymentTermCalculator, CreatedInvoicePublisher invoicePublisher, InvoiceEventStorage invoiceEventStorage) {
        return InvoiceFacadeBuilder.create(invoiceNumberCalculator, invoicePaymentTermCalculator, invoicePublisher, invoiceEventStorage);
    }

}
