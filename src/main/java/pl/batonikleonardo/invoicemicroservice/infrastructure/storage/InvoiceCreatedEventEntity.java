package pl.batonikleonardo.invoicemicroservice.infrastructure.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class InvoiceCreatedEventEntity {
    private String name;
    private String email;
    private String address;
    private String city;
    private String postalCode;
    private String province;
    private String country;
    private String taxIdentificationNumber;
}
