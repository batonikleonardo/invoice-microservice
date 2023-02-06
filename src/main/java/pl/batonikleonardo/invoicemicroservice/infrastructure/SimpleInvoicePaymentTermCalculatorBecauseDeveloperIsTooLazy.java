package pl.batonikleonardo.invoicemicroservice.infrastructure;

import org.springframework.stereotype.Component;
import pl.batonikleonardo.invoicemicroservice.domain.InvoiceDate;
import pl.batonikleonardo.invoicemicroservice.domain.InvoicePaymentTermCalculator;

@Component
class SimpleInvoicePaymentTermCalculatorBecauseDeveloperIsTooLazy implements InvoicePaymentTermCalculator {
    @Override
    public InvoiceDate calculate(InvoiceDate issuedDate) {
        return issuedDate.plus(14);
    }
}
