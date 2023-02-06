package pl.batonikleonardo.invoicemicroservice.domain;

public class InvoiceNumberAssignedToOrderEvent {
    private final SourceOrderId sourceOrderId;
    private final InvoiceNumber invoiceNumber;

    public InvoiceNumberAssignedToOrderEvent(SourceOrderId sourceOrderId, InvoiceNumber invoiceNumber) {
        this.sourceOrderId = sourceOrderId;
        this.invoiceNumber = invoiceNumber;
    }

    public long sourceOrderIdValue() {
        return sourceOrderId.value();
    }

    public String invoiceNumberValue() {
        return invoiceNumber.value();
    }
}

