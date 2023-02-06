package pl.batonikleonardo.invoicemicroservice.infrastructure.storage;

import lombok.Data;

@Data
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
