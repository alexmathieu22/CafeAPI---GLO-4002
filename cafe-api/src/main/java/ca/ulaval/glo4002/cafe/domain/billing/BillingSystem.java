package ca.ulaval.glo4002.cafe.domain.billing;

import java.util.HashMap;

import ca.ulaval.glo4002.cafe.domain.TipRate;
import ca.ulaval.glo4002.cafe.domain.billing.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.bill.BillFactory;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNoBillException;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.geolocalisation.Location;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.ordering.OrderingSystem;

public class BillingSystem {
    private HashMap<CustomerId, Bill> bills = new HashMap<>();
    private BillFactory billFactory;

    public BillingSystem(BillFactory billFactory) {
        this.billFactory = billFactory;
    }

    public void createBill(CustomerId customerId, OrderingSystem orderingSystem, Location location, TipRate groupTipRate, boolean isInGroup) {
        Bill bill = billFactory.createBill(orderingSystem.getOrderByCustomerId(customerId), location, groupTipRate, isInGroup);
        bills.put(customerId, bill);
    }

    public void clear() {
        bills.clear();
    }

    public Bill getBillByCustomerId(CustomerId customerId) {
        return bills.get(customerId);
    }

    public boolean hasBillForCustomerId(CustomerId customerId) {
        return bills.containsKey(customerId);
    }
}
