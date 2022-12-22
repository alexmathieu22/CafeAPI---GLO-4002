package ca.ulaval.glo4002.cafe.small.cafe.domain.ordering;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.ordering.OrderingSystem;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Order;
import ca.ulaval.glo4002.cafe.fixture.OrderFixture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OrderingSystemTest {
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("customer-id");
    private static final Order AN_ORDER = new OrderFixture().build();

    private OrderingSystem orderingSystem;
    private Inventory inventory;

    @BeforeEach
    public void createOrderingSystem() {
        orderingSystem = new OrderingSystem();
        inventory = mock(Inventory.class);
    }

    @Test
    public void givenInventory_whenPlacingOrder_shouldUseIngredients() {
        orderingSystem.placeOrder(A_CUSTOMER_ID, AN_ORDER, inventory);

        verify(inventory).useIngredients(AN_ORDER.ingredientsNeeded());
    }

    @Test
    public void whenPlacingOrder_shouldAddCorrectOrder() {
        orderingSystem.placeOrder(A_CUSTOMER_ID, AN_ORDER, inventory);

        assertEquals(AN_ORDER, orderingSystem.getOrderByCustomerId(A_CUSTOMER_ID));
    }

    @Test
    public void givenNonEmptyOrderingSystem_whenClearing_shouldClear() {
        orderingSystem.placeOrder(A_CUSTOMER_ID, AN_ORDER, inventory);

        orderingSystem.clear();

        assertEquals(orderingSystem.getOrderByCustomerId(A_CUSTOMER_ID), new Order(List.of()));
    }

    @Test
    public void givenCustomerWithNoOrder_whenGettingOrderByCustomerId_shouldReturnEmptyList() {
        assertEquals(orderingSystem.getOrderByCustomerId(A_CUSTOMER_ID), new Order(List.of()));
    }
}
