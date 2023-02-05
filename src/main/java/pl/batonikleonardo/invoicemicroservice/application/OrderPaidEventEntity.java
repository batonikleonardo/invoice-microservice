package pl.batonikleonardo.invoicemicroservice.application;

import java.util.Objects;

final class OrderPaidEventEntity {
    private final String name;
    private final String email;
    private final String taxIdentificationNumber;
    private final OrderPaidEventAddress orderPaidEventAddress;

    OrderPaidEventEntity(String name, String email, String taxIdentificationNumber,
                         OrderPaidEventAddress orderPaidEventAddress) {
        this.name = name;
        this.email = email;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.orderPaidEventAddress = orderPaidEventAddress;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public String taxIdentificationNumber() {
        return taxIdentificationNumber;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (OrderPaidEventEntity) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.email, that.email) &&
                Objects.equals(this.taxIdentificationNumber, that.taxIdentificationNumber) &&
                Objects.equals(this.orderPaidEventAddress, that.orderPaidEventAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, taxIdentificationNumber, orderPaidEventAddress);
    }

    @Override
    public String toString() {
        return "OrderPaidEventEntity[" +
                "name=" + name + ", " +
                "email=" + email + ", " +
                "taxIdentificationNumber=" + taxIdentificationNumber + ", " +
                "orderPaidEventAddress=" + orderPaidEventAddress + ']';
    }

    public String address() {
        return orderPaidEventAddress.address();
    }

    public String city() {
        return orderPaidEventAddress.city();
    }

    public String postalCode() {
        return orderPaidEventAddress.postalCode();
    }

    public String province() {
        return orderPaidEventAddress.province();
    }

    public String country() {
        return orderPaidEventAddress.country();
    }
}
