package pl.batonikleonardo.invoicemicroservice.domain;

import pl.batonikleonardo.invoicemicroservice.domain.exception.IncorrectInvoiceInformationPartException;

import java.util.Objects;

public final class InvoiceInformation {
    private final String name;
    private final String email;
    private final String address;
    private final String city;
    private final String postalCode;
    private final String province;
    private final String country;
    private final String taxIdentificationNumber;

    public InvoiceInformation(String name, String email, String address, String city, String postalCode, String province,
                              String country, String taxIdentificationNumber) throws IncorrectInvoiceInformationPartException {
        selfValidation(name, email, address, city, postalCode, province, country, taxIdentificationNumber);
        this.name = name;
        this.email = email;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
        this.country = country;
        this.taxIdentificationNumber = taxIdentificationNumber;
    }


    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public String address() {
        return address;
    }

    public String city() {
        return city;
    }

    public String postalCode() {
        return postalCode;
    }

    public String province() {
        return province;
    }

    public String country() {
        return country;
    }

    public String taxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    private void selfValidation(String name, String email, String address, String city, String postalCode, String province, String country, String taxIdentificationNumber) throws IncorrectInvoiceInformationPartException {
        //TODO update this simple validation to something more correct
        if (isNotValid(name)) {
            throw new IncorrectInvoiceInformationPartException("Name", name);
        }
        if (isNotValid(email)) {
            throw new IncorrectInvoiceInformationPartException("Email", email);
        }
        if (isNotValid(address)) {
            throw new IncorrectInvoiceInformationPartException("Address", address);
        }
        if (isNotValid(city)) {
            throw new IncorrectInvoiceInformationPartException("City", city);
        }
        if (isNotValid(postalCode)) {
            throw new IncorrectInvoiceInformationPartException("Postal Code", postalCode);
        }
        if (isNotValid(province)) {
            throw new IncorrectInvoiceInformationPartException("Province", province);
        }
        if (isNotValid(country)) {
            throw new IncorrectInvoiceInformationPartException("Country", country);
        }
        if (isNotValid(taxIdentificationNumber)) {
            throw new IncorrectInvoiceInformationPartException("Tax Identification Number", taxIdentificationNumber);
        }
    }

    private boolean isNotValid(String value) {
        return Objects.isNull(value) || value.isBlank();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (InvoiceInformation) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.email, that.email) &&
                Objects.equals(this.address, that.address) &&
                Objects.equals(this.city, that.city) &&
                Objects.equals(this.postalCode, that.postalCode) &&
                Objects.equals(this.province, that.province) &&
                Objects.equals(this.country, that.country) &&
                Objects.equals(this.taxIdentificationNumber, that.taxIdentificationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, address, city, postalCode, province, country, taxIdentificationNumber);
    }

    @Override
    public String toString() {
        return "InvoiceInformation[" +
                "name=" + name + ", " +
                "email=" + email + ", " +
                "address=" + address + ", " +
                "city=" + city + ", " +
                "postalCode=" + postalCode + ", " +
                "province=" + province + ", " +
                "country=" + country + ", " +
                "taxIdentificationNumber=" + taxIdentificationNumber + ']';
    }


}
