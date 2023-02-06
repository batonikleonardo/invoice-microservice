package pl.batonikleonardo.invoicemicroservice.infrastructure.storage;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invoiceDocument")
@Data
class InvoiceNumberAssignedToOrderDocument {
    @Id
    private Long sourceOrderId;
    private String number;

    public InvoiceNumberAssignedToOrderDocument(long sourceOrderId, String invoiceNumber) {
        this.sourceOrderId = sourceOrderId;
        this.number = invoiceNumber;
    }

}

