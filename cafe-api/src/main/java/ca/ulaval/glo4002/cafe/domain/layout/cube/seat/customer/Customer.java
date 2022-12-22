package ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.TipRate;
import ca.ulaval.glo4002.cafe.domain.billing.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.bill.BillFactory;
import ca.ulaval.glo4002.cafe.domain.geolocalisation.Location;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Order;

public class Customer {
    private final CustomerId id;
    private final CustomerName name;
    private final BillFactory billFactory;
    private Order order = new Order(List.of());

    public Customer(CustomerId customerId, CustomerName customerName) {
        this.id = customerId;
        this.name = customerName;
        this.billFactory = new BillFactory();
    }

    public CustomerId getId() {
        return id;
    }

    public CustomerName getName() {
        return name;
    }

    public Bill createBill(Location location, TipRate groupTipRate, boolean isInGroup) {
        return billFactory.createBill(order, location, groupTipRate, isInGroup);
    }
}
