package pl.batonikleonardo.invoicemicroservice.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class InvoiceTest {

    @Test
    void whenBuildEmptyInvoiceThenThrowException() {
        final InvoiceBuilder invoiceBuilder = Invoice.builder();

        assertThatExceptionOfType(Invoice.invoiceMissingOrIncorrectFieldException.class)
                .isThrownBy(invoiceBuilder::build);
    }

    @Test
    void buildInvoiceHasAllData() throws InvoiceNumber.IncorrectInvoiceNumberException, InvoiceDate.InvoiceDataIsPastException, SourceOrderId.IncorrectSourceOrderIdException, InvoiceItem.IncorrectInvoiceItemException, InvoiceInformation.IncorrectInvoiceInformationPartException {
        //given
        final ZonedDateTime invoiceDateTime = ZonedDateTime.now().plusDays(4);
        final ZonedDateTime paymentDateTime = ZonedDateTime.now().plusDays(12);

        final InvoiceNumber invoiceNumber = new InvoiceNumber("2023/02/04/5638");
        final InvoiceDate issuedDate = new InvoiceDate(invoiceDateTime);
        final InvoiceDate paymentTerm = new InvoiceDate(paymentDateTime);
        final SourceOrderId sourceOrderId = new SourceOrderId(300477);
        double taxValue = 10;

        final InvoiceInformation company = new InvoiceInformation("my company", "my.company@mail.test", "Street 1", "City 1",
                "88-345", "Province 1", "Country 1", "88665434324532");
        final InvoiceInformation client = new InvoiceInformation("drunk client", "d.client@mail.test", "Street 30", "City 3",
                "76-351", "Province 1", "Country 1", "886643234254");

        final BigDecimal brushPrice = BigDecimal.valueOf(125);
        final BigDecimal pastePrice = BigDecimal.valueOf(30);
        final BigDecimal keyPrice = BigDecimal.valueOf(2);

        final InvoiceItem invoiceItem1 = new InvoiceItem("Eye cleaning brush", null, 1, brushPrice);
        final InvoiceItem invoiceItem2 = new InvoiceItem("Eye paste with diamond abrasive", null, 4, pastePrice);
        final InvoiceItem invoiceItem3 = new InvoiceItem("Key to Yennefer's room", "Common Item", 2, keyPrice);

        final BigDecimal calculateInvoiceItemsSubtotalValue = BigDecimal.ZERO.add(brushPrice)
                .add(keyPrice)
                .add(keyPrice)
                .add(pastePrice)
                .add(pastePrice)
                .add(pastePrice)
                .add(pastePrice);

        final BigDecimal calculateInvoiceItemsTotalValue = calculateInvoiceItemsSubtotalValue
                .add(calculateInvoiceItemsSubtotalValue
                        .multiply(BigDecimal.valueOf(taxValue)
                                .movePointLeft(2))
                );
        //then

        assertThatNoException().isThrownBy(() -> {
            final Invoice resultInvoice = Invoice.builder()
                    .invoiceNumber(invoiceNumber)
                    .issuedDate(issuedDate)
                    .paymentTerm(paymentTerm)
                    .company(company)
                    .client(client)
                    .invoiceItem(invoiceItem1)
                    .invoiceItem(invoiceItem2)
                    .invoiceItem(invoiceItem3)
                    .sourceOrderId(sourceOrderId)
                    .taxValue(taxValue)
                    .build();

            assertThat(resultInvoice.invoiceNumberValue()).isEqualTo("2023/02/04/5638");
            assertThat(resultInvoice.issuedDateValue()).isEqualTo(invoiceDateTime);
            assertThat(resultInvoice.paymentTermValue()).isEqualTo(paymentDateTime);
            assertThat(resultInvoice.sourceOrderIdValue()).isEqualTo(300477);

            assertThat(resultInvoice.companyName()).isEqualTo("my company");
            assertThat(resultInvoice.companyEmail()).isEqualTo("my.company@mail.test");
            assertThat(resultInvoice.companyAddress()).isEqualTo("Street 1");
            assertThat(resultInvoice.companyCity()).isEqualTo("City 1");
            assertThat(resultInvoice.companyProvince()).isEqualTo("Province 1");
            assertThat(resultInvoice.companyCountry()).isEqualTo("Country 1");
            assertThat(resultInvoice.companyPostalCode()).isEqualTo("88-345");
            assertThat(resultInvoice.companyTaxIdentificationNumber()).isEqualTo("88665434324532");


            assertThat(resultInvoice.clientName()).isEqualTo("drunk client");
            assertThat(resultInvoice.clientEmail()).isEqualTo("d.client@mail.test");
            assertThat(resultInvoice.clientAddress()).isEqualTo("Street 30");
            assertThat(resultInvoice.clientCity()).isEqualTo("City 3");
            assertThat(resultInvoice.clientProvince()).isEqualTo("Province 1");
            assertThat(resultInvoice.clientCountry()).isEqualTo("Country 1");
            assertThat(resultInvoice.clientPostalCode()).isEqualTo("76-351");
            assertThat(resultInvoice.clientTaxIdentificationNumber()).isEqualTo("886643234254");

            assertThat(resultInvoice.summaryTaxValue()).isEqualTo(taxValue);
            assertThat(resultInvoice.summarySubtotal()).isEqualTo(calculateInvoiceItemsSubtotalValue);
            assertThat(resultInvoice.summaryTotal()).isEqualTo(calculateInvoiceItemsTotalValue);

            final List<InvoiceItem> invoiceItems = resultInvoice.invoiceItemsList();
            assertThat(invoiceItems).hasSize(3)
                    .extracting(InvoiceItem::name, InvoiceItem::description, InvoiceItem::quantity, InvoiceItem::totalPrice)
                    .contains(
                            tuple("Eye cleaning brush", null, 1, brushPrice),
                            tuple("Eye paste with diamond abrasive", null, 4, pastePrice.add(pastePrice).add(pastePrice).add(pastePrice)),
                            tuple("Key to Yennefer's room", "Common Item", 2, keyPrice.add(keyPrice))
                    );
        });
    }

}