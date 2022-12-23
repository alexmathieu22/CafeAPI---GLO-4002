package ca.ulaval.glo4002.cafe.domain.billing;

import java.util.HashMap;

import ca.ulaval.glo4002.cafe.domain.billing.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.bill.BillFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.ordering.OrderingSystem;
import ca.ulaval.glo4002.cafe.domain.taxing.LocationTax;

public class BillingSystem {
    private final HashMap<CustomerId, Bill> bills = new HashMap<>();
    private final BillFactory billFactory;

    public BillingSystem(BillFactory billFactory) {
        this.billFactory = billFactory;
    }

    public void createBill(CustomerId customerId, OrderingSystem orderingSystem, LocationTax locationTax, TipRate groupTipRate, boolean isInGroup) {
        Bill bill = billFactory.createBill(orderingSystem.getOrderByCustomerId(customerId), locationTax.getTaxPercentage(), groupTipRate, isInGroup);
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
