package pl.batonikleonardo.invoicemicroservice.domain;

public interface InvoicePaymentTermCalculator {
    InvoiceDate calculate(InvoiceDate issuedDate);

}
