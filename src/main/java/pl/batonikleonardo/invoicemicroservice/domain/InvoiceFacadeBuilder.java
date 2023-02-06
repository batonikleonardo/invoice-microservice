package pl.batonikleonardo.invoicemicroservice.domain;

public class InvoiceFacadeBuilder {
    private InvoiceFacadeBuilder() {
    }

    public static InvoiceFacade create(InvoiceNumberCalculator invoiceNumberCalculator,
                                       InvoicePaymentTermCalculator paymentTermCalculator,
                                       CreatedInvoicePublisher invoicePublisher,
                                       InvoiceEventStorage invoiceEventStorage) {

        return new InvoiceFacade(invoiceNumberCalculator, paymentTermCalculator, invoicePublisher, invoiceEventStorage);
    }
}
