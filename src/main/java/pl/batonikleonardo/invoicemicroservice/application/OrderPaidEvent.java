package pl.batonikleonardo.invoicemicroservice.application;

import java.time.ZonedDateTime;
import java.util.List;

record OrderPaidEvent(int orderId, ZonedDateTime paidDate, OrderPaidEventEntity company, OrderPaidEventEntity client,
                      List<OrderItem> items, double tax) {

}
