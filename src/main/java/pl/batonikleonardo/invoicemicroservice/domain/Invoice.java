package pl.batonikleonardo.invoicemicroservice.domain;

import pl.batonikleonardo.invoicemicroservice.domain.exception.IncorrectInvoiceSummaryException;
import pl.batonikleonardo.invoicemicroservice.domain.exception.InvoiceMissingOrIncorrectFieldException;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public class Invoice {
    private final InvoiceNumber invoiceNumber;
    private final InvoiceDate issuedDate;
    private final InvoiceDate paymentTerm;
    private final InvoiceInformation company;
    private final InvoiceInformation client;
    private final InvoiceItems invoiceItems;
    private final InvoiceSummary invoiceSummary;
    private final SourceOrderId sourceOrderId;

    Invoice(InvoiceNumber invoiceNumber, InvoiceInformation company, InvoiceInformation client, InvoiceDate issuedDate, InvoiceDate paymentTerm, InvoiceItems invoiceItems, double taxValue, SourceOrderId sourceOrderId) throws InvoiceMissingOrIncorrectFieldException, IncorrectInvoiceSummaryException {
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

    private void selfValidation(InvoiceNumber invoiceNumber, InvoiceInformation company, InvoiceInformation client, InvoiceDate issuedDate, InvoiceDate paymentTerm, InvoiceItems invoiceItems, double taxValue, SourceOrderId sourceOrderId) throws InvoiceMissingOrIncorrectFieldException {
        if (Objects.isNull(invoiceNumber)) {
            throw new InvoiceMissingOrIncorrectFieldException("invoice number");
        }
        if (Objects.isNull(company)) {
            throw new InvoiceMissingOrIncorrectFieldException("company");
        }
        if (Objects.isNull(client)) {
            throw new InvoiceMissingOrIncorrectFieldException("client");
        }
        if (Objects.isNull(issuedDate)) {
            throw new InvoiceMissingOrIncorrectFieldException("issued date");
        }
        if (Objects.isNull(paymentTerm)) {
            throw new InvoiceMissingOrIncorrectFieldException("payment term");
        }
        if (Objects.isNull(invoiceItems)) {
            throw new InvoiceMissingOrIncorrectFieldException("invoice items");
        }
        if (Objects.isNull(sourceOrderId)) {
            throw new InvoiceMissingOrIncorrectFieldException("source order id");
        }
        if (taxValue <= 0) {
            throw new InvoiceMissingOrIncorrectFieldException("tax value");
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

    public InvoiceSummary summary() {
        return invoiceSummary;
    }

    public InvoiceDate issuedDate() {
        return issuedDate;
    }

    public InvoiceDate paymentTerm() {
        return paymentTerm;
    }

    public InvoiceInformation company() {
        return company;
    }

    public InvoiceInformation client() {
        return client;
    }

    public InvoiceItems items() {
        return invoiceItems;
    }

    public List<InvoiceItem> invoiceItemsList() {
        return invoiceItems.asList();
    }


    public InvoiceNumber number() {
        return invoiceNumber;
    }

    public SourceOrderId sourceOrderId() {
        return sourceOrderId;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceNumber=" + invoiceNumber +
                ", issuedDate=" + issuedDate +
                ", paymentTerm=" + paymentTerm +
                ", company=" + company +
                ", client=" + client +
                ", invoiceItems=" + invoiceItems +
                ", invoiceSummary=" + invoiceSummary +
                ", sourceOrderId=" + sourceOrderId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice invoice)) return false;
        return Objects.equals(invoiceNumber, invoice.invoiceNumber) && Objects.equals(issuedDate, invoice.issuedDate) && Objects.equals(paymentTerm, invoice.paymentTerm) && Objects.equals(company, invoice.company) && Objects.equals(client, invoice.client) && Objects.equals(invoiceItems, invoice.invoiceItems) && Objects.equals(invoiceSummary, invoice.invoiceSummary) && Objects.equals(sourceOrderId, invoice.sourceOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceNumber, issuedDate, paymentTerm, company, client, invoiceItems, invoiceSummary, sourceOrderId);
    }

    public String numberAsString() {
        return invoiceNumber.value();
    }
}
