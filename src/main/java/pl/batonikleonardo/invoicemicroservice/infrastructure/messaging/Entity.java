package pl.batonikleonardo.invoicemicroservice.infrastructure.messaging;

import lombok.Data;

@Data
class Entity {
    private String name;
    private String email;
    private String address;
    private String city;
    private String postalCode;
    private String province;
    private String country;
    private String taxIdentificationNumber;

}
