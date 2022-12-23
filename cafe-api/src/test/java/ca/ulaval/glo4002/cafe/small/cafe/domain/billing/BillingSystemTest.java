package ca.ulaval.glo4002.cafe.small.cafe.domain.billing;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.billing.BillingSystem;
import ca.ulaval.glo4002.cafe.domain.billing.TipRate;
import ca.ulaval.glo4002.cafe.domain.billing.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.bill.BillFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.ordering.OrderingSystem;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Order;
import ca.ulaval.glo4002.cafe.domain.taxing.CountryTax;
import ca.ulaval.glo4002.cafe.domain.taxing.LocationTax;
import ca.ulaval.glo4002.cafe.domain.taxing.Tax;
import ca.ulaval.glo4002.cafe.domain.valueobjects.Amount;
import ca.ulaval.glo4002.cafe.fixture.OrderFixture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BillingSystemTest {
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("customer-id");
    private static final LocationTax A_LOCATION_TAX = new LocationTax(CountryTax.None, Optional.empty(), Optional.empty());
    private static final Tax A_TAX_RATE = new Tax(0);
    private static final TipRate A_TIP_RATE = new TipRate(0);
    private static final boolean IS_IN_GROUP = false;
    private static final Order AN_ORDER = new OrderFixture().build();
    private static final Amount A_TOTAL = new Amount(15);
    private static final Amount A_TAX = new Amount(0);
    private static final Amount A_TIP = new Amount(0);
    private static final Bill A_BILL = new Bill(AN_ORDER, A_TOTAL, A_TAX, A_TIP);
    private OrderingSystem orderingSystem;
    private BillingSystem billingSystem;
    private BillFactory billFactory;

    @BeforeEach
    public void createBillingSystem() {
        billFactory = mock(BillFactory.class);
        billingSystem = new BillingSystem(billFactory);
    }

    @BeforeEach
    public void createOrderingSystem() {
        orderingSystem = mock(OrderingSystem.class);
    }

    @Test
    public void whenCreatingBill_shouldAddCorrectBill() {
        when(orderingSystem.getOrderByCustomerId(any())).thenReturn(AN_ORDER);
        when(billFactory.createBill(AN_ORDER, A_TAX_RATE, A_TIP_RATE, IS_IN_GROUP)).thenReturn(A_BILL);

        billingSystem.createBill(A_CUSTOMER_ID, orderingSystem, A_LOCATION_TAX, A_TIP_RATE, IS_IN_GROUP);

        assertEquals(A_BILL, billingSystem.getBillByCustomerId(A_CUSTOMER_ID));
    }

    @Test
    public void givenNonEmptyBillingSystem_whenClearing_shouldClear() {
        billingSystem.createBill(A_CUSTOMER_ID, orderingSystem, A_LOCATION_TAX, A_TIP_RATE, IS_IN_GROUP);

        billingSystem.clear();

        assertNull(billingSystem.getBillByCustomerId(A_CUSTOMER_ID));
    }

    @Test
    public void givenCustomerBill_whenHasBillForCustomerId_shouldReturnTrue() {
        billingSystem.createBill(A_CUSTOMER_ID, orderingSystem, A_LOCATION_TAX, A_TIP_RATE, IS_IN_GROUP);

        boolean hasBill = billingSystem.hasBillForCustomerId(A_CUSTOMER_ID);

        assertTrue(hasBill);
    }

    @Test
    public void givenNoCustomerBill_whenHasBillForCustomerId_shouldReturnTrue() {
        boolean hasBill = billingSystem.hasBillForCustomerId(A_CUSTOMER_ID);

        assertFalse(hasBill);
    }
}
