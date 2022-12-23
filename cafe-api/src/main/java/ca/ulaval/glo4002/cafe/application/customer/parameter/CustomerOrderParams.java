package ca.ulaval.glo4002.cafe.application.customer.parameter;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Order;

public record CustomerOrderParams(CustomerId customerId, Order order) {
    public CustomerOrderParams(String customerId, List<Coffee> orders) {
        this(new CustomerId(customerId), new Order(orders));
    }
}
