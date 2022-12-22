package ca.ulaval.glo4002.cafe.application.customer.dto;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.ordering.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Order;

public record OrderDTO(List<Coffee> coffees) {
    public static OrderDTO fromOrder(Order order) {
        return new OrderDTO(order.items().stream().toList());
    }
}
