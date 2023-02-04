package pl.batonikleonardo.invoicemicroservice.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

class Invoice {
    private final InvoiceNumber invoiceNumber;
    private final InvoiceDate issuedDate;
    private final InvoiceDate paymentTerm;
    private final InvoiceInformation company;
    private final InvoiceInformation client;
    private final InvoiceItems invoiceItems;
    private final InvoiceSummary invoiceSummary;
    private final SourceOrderId sourceOrderId;

    Invoice(InvoiceNumber invoiceNumber, InvoiceInformation company, InvoiceInformation client, InvoiceDate issuedDate, InvoiceDate paymentTerm, InvoiceItems invoiceItems, double taxValue, SourceOrderId sourceOrderId) throws invoiceMissingOrIncorrectFieldException, InvoiceSummary.IncorrectInvoiceSummaryException {
        selfValidation(invoiceNumber, company, client, issuedDate, paymentTerm, invoiceItems, taxValue, sourceOrderId);
        this.invoiceNumber = invoiceNumber;
        this.company = company;
        this.client = client;
        this.issuedDate = issuedDate;
        this.paymentTerm = paymentTerm;
        this.invoiceItems = invoiceItems;
        this.invoiceSummary = new InvoiceSummary(invoiceItems, taxValue);
        this.sourceOrderId = sourceOrderId;
    }

    private void selfValidation(InvoiceNumber invoiceNumber, InvoiceInformation company, InvoiceInformation client, InvoiceDate issuedDate, InvoiceDate paymentTerm, InvoiceItems invoiceItems, double taxValue, SourceOrderId sourceOrderId) throws invoiceMissingOrIncorrectFieldException {
        if (Objects.isNull(invoiceNumber)) {
            throw new invoiceMissingOrIncorrectFieldException("invoice number");
        }
        if (Objects.isNull(company)) {
            throw new invoiceMissingOrIncorrectFieldException("company");
        }
        if (Objects.isNull(client)) {
            throw new invoiceMissingOrIncorrectFieldException("client");
        }
        if (Objects.isNull(issuedDate)) {
            throw new invoiceMissingOrIncorrectFieldException("issued date");
        }
        if (Objects.isNull(paymentTerm)) {
            throw new invoiceMissingOrIncorrectFieldException("payment term");
        }
        if (Objects.isNull(invoiceItems)) {
            throw new invoiceMissingOrIncorrectFieldException("invoice items");
        }
        if (Objects.isNull(sourceOrderId)) {
            throw new invoiceMissingOrIncorrectFieldException("source order id");
        }
        if (taxValue <= 0) {
            throw new invoiceMissingOrIncorrectFieldException("tax value");
        }
    }

    public static InvoiceBuilder builder() {
        return new InvoiceBuilder();
    }

    public String invoiceNumberValue() {
        return invoiceNumber.value();
    }

    public ZonedDateTime issuedDateValue() {
        return issuedDate.invoiceDateTime();
    }

    public ZonedDateTime paymentTermValue() {
        return paymentTerm.invoiceDateTime();
    }

    public String companyName() {
        return company.name();
    }

    public String companyEmail() {
        return company.email();
    }

    public String companyAddress() {
        return company.address();
    }

    public String companyCity() {
        return company.city();
    }

    public String companyPostalCode() {
        return company.postalCode();
    }

    public String companyProvince() {
        return company.province();
    }

    public String companyCountry() {
        return company.country();
    }

    public String companyTaxIdentificationNumber() {
        return company.taxIdentificationNumber();
    }

    public String clientName() {
        return client.name();
    }

    public String clientEmail() {
        return client.email();
    }

    public String clientAddress() {
        return client.address();
    }

    public String clientCity() {
        return client.city();
    }

    public String clientPostalCode() {
        return client.postalCode();
    }

    public String clientProvince() {
        return client.province();
    }

    public String clientCountry() {
        return client.country();
    }

    public String clientTaxIdentificationNumber() {
        return client.taxIdentificationNumber();
    }

    public long sourceOrderIdValue() {
        return sourceOrderId.value();
    }

    public double summaryTaxValue() {
        return invoiceSummary.taxValueAsDouble();
    }

    public BigDecimal summarySubtotal() {
        return invoiceSummary.subtotal();
    }

    public BigDecimal summaryTotal() {
        return invoiceSummary.total();
    }

    public List<InvoiceItem> invoiceItemsList() {
        return invoiceItems.asList();
    }


    static class invoiceMissingOrIncorrectFieldException extends Exception {
        private invoiceMissingOrIncorrectFieldException(String fieldName) {
            super(String.format("Missing or incorrect invoice field = %s", fieldName));

        }
    }
}
