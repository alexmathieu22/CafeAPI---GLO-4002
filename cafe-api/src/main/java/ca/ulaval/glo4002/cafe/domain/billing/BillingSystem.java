package ca.ulaval.glo4002.cafe.domain.billing;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.cafe.domain.billing.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.bill.BillFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.ordering.OrderingSystem;
import ca.ulaval.glo4002.cafe.domain.taxing.CountryTax;
import ca.ulaval.glo4002.cafe.domain.taxing.Location;
import ca.ulaval.glo4002.cafe.domain.taxing.strategy.TaxingCanada;
import ca.ulaval.glo4002.cafe.domain.taxing.strategy.TaxingNone;
import ca.ulaval.glo4002.cafe.domain.taxing.strategy.TaxingStrategy;
import ca.ulaval.glo4002.cafe.domain.taxing.strategy.TaxingUnitedStates;

public class BillingSystem {
    private static final Map<CountryTax, TaxingStrategy> TAXING_STRATEGIES =
        Map.of(CountryTax.CA, new TaxingCanada(), CountryTax.US, new TaxingUnitedStates(), CountryTax.CL, new TaxingNone(), CountryTax.None, new TaxingNone());
    private final HashMap<CustomerId, Bill> bills = new HashMap<>();
    private final BillFactory billFactory;

    public BillingSystem(BillFactory billFactory) {
        this.billFactory = billFactory;
    }

    public void createBill(CustomerId customerId, OrderingSystem orderingSystem, Location location, TipRate groupTipRate, boolean isInGroup) {
        Bill bill = billFactory.createBill(orderingSystem.getOrderByCustomerId(customerId), location.getTaxPercentage(), groupTipRate, isInGroup);
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
