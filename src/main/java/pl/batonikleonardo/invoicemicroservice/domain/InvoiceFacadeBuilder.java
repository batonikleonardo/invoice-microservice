package pl.batonikleonardo.invoicemicroservice.domain;

public class InvoiceFacadeBuilder {
    private InvoiceFacadeBuilder() {
    }

    public static InvoiceFacade create() {
        return new InvoiceFacade();
    }
}
