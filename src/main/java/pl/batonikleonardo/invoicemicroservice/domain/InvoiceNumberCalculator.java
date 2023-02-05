package pl.batonikleonardo.invoicemicroservice.domain;

public interface InvoiceNumberCalculator {
    InvoiceNumber calculate(InvoiceDate issuedDate);
}
