package ca.ulaval.glo4002.cafe.domain.ordering;

import java.util.HashMap;
import java.util.List;

import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Order;

public class OrderingSystem {
    private final HashMap<CustomerId, Order> orders = new HashMap<>();

    public void placeOrder(CustomerId customerId, Order order, Inventory inventory) {
        inventory.useIngredients(order.ingredientsNeeded());
        orders.put(customerId, orders.getOrDefault(customerId, new Order(List.of())).addAll(order));
    }

    public Order getOrderByCustomerId(CustomerId customerId) {
        return orders.getOrDefault(customerId, new Order(List.of()));
    }

    public void clear() {
        orders.clear();
    }
}
