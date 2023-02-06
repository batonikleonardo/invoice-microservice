package pl.batonikleonardo.invoicemicroservice.infrastructure;

import org.springframework.stereotype.Component;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceDate;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceNumber;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceNumberCalculator;

import java.util.UUID;

@Component
class SimpleInvoiceNumberCalculatorBecauseDeveloperIsTooLazy implements InvoiceNumberCalculator {
    @Override
    public InvoiceNumber calculate(InvoiceDate issuedDate) {
        final String sequence = issuedDate.asSlashSequenceWithUUID(UUID.randomUUID());
        return new InvoiceNumber(sequence);
    }
}
