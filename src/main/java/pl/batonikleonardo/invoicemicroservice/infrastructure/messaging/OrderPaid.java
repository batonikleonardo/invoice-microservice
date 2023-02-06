package pl.batonikleonardo.invoicemicroservice.infrastructure.messaging;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
class OrderPaid {
    private long orderId;
    private double taxValue;
    private ZonedDateTime date;
    private OrderPaidEntity client;
    private OrderPaidEntity company;
    private List<OrderPaidItem> items;
}
