package pl.batonikleonardo.invoicemicroservice.infrastructure.messaging;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.batonikleonardo.invoicemicroservice.application.OrderItem;
import pl.batonikleonardo.invoicemicroservice.application.OrderPaidEvent;
import pl.batonikleonardo.invoicemicroservice.application.OrderPaidEventEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class OrderPaidEventMapper {
    static OrderPaidEvent map(OrderPaid orderPaid) {
        final long orderId = orderPaid.getOrderId();
        final ZonedDateTime paidDate = orderPaid.getDate();

        final OrderPaidEventEntity company = mapEntity(orderPaid.getCompany());
        final OrderPaidEventEntity client = mapEntity(orderPaid.getClient());
        final List<OrderItem> orderItems = mapItemList(orderPaid.getItems());

        return new OrderPaidEvent(orderId, paidDate, company, client, orderItems, 20);
    }

    private static OrderPaidEventEntity mapEntity(Entity company) {
        return OrderPaidEventEntity.create(company.getName(), company.getEmail(), company.getTaxIdentificationNumber(),
                company.getAddress(), company.getCity(), company.getPostalCode(), company.getProvince(), company.getCountry());
    }

    private static List<OrderItem> mapItemList(List<Item> items) {
        return items.stream()
                .map(OrderPaidEventMapper::mapItem)
                .toList();
    }

    private static OrderItem mapItem(Item orderPaidItem) {
        return OrderItem.create(orderPaidItem.getName(), orderPaidItem.getDescription(), orderPaidItem.getQuantity(), orderPaidItem.getPrice());
    }
}
