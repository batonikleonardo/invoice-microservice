package pl.batonikleonardo.invoicemicroservice.domain;

class InvoiceBuilder {
    private InvoiceNumber invoiceNumber;
    private InvoiceDate issuedDate;
    private InvoiceDate paymentTerm;
    private InvoiceInformation company;
    private InvoiceInformation client;
    private InvoiceItems invoiceItems;
    double taxValue;

    private SourceOrderId sourceOrderId;

    InvoiceBuilder() {
        this.invoiceItems = new InvoiceItems();
    }

    public InvoiceBuilder invoiceNumber(InvoiceNumber invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public InvoiceBuilder issuedDate(InvoiceDate issuedDate) {
        this.issuedDate = issuedDate;
        return this;
    }

    public InvoiceBuilder paymentTerm(InvoiceDate paymentTerm) {
        this.paymentTerm = paymentTerm;
        return this;
    }

    public InvoiceBuilder company(InvoiceInformation company) {
        this.company = company;
        return this;
    }

    public InvoiceBuilder client(InvoiceInformation client) {
        this.client = client;
        return this;
    }

    public InvoiceBuilder invoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItems.add(invoiceItem);
        return this;
    }

    public InvoiceBuilder sourceOrderId(SourceOrderId sourceOrderId) {
        this.sourceOrderId = sourceOrderId;
        return this;
    }

    public Invoice build() throws Invoice.invoiceMissingOrIncorrectFieldException, InvoiceSummary.IncorrectInvoiceSummaryException {
        return new Invoice(invoiceNumber, company, client, issuedDate, paymentTerm, invoiceItems, taxValue, sourceOrderId);
    }

    public InvoiceBuilder taxValue(double taxValue) {
        this.taxValue = taxValue;
        return this;
    }
}
