package ca.ulaval.glo4002.cafe.small.cafe.api.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.customer.CustomerResource;
import ca.ulaval.glo4002.cafe.api.customer.request.OrderRequest;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.customer.dto.BillDTO;
import ca.ulaval.glo4002.cafe.application.customer.dto.CustomerDTO;
import ca.ulaval.glo4002.cafe.application.customer.dto.OrderDTO;
import ca.ulaval.glo4002.cafe.application.customer.parameter.CustomerOrderParams;
import ca.ulaval.glo4002.cafe.application.menu.MenuService;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerName;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Order;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Recipe;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.fixture.request.OrderRequestFixture;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerResourceTest {
    private static final CustomerId CUSTOMER_ID = new CustomerId("customerId");
    private static final CustomerName CUSTOMER_NAME = new CustomerName("customerName");
    private static final GroupName GROUP_NAME = new GroupName("groupName");
    private static final SeatNumber SEAT_NUMBER = new SeatNumber(1);
    private static final Amount AMOUNT = new Amount(0);
    private static final Order ORDER = new Order(new ArrayList<>());
    private static final CustomerDTO A_CUSTOMER_DTO = new CustomerDTO(CUSTOMER_NAME, SEAT_NUMBER, Optional.of(GROUP_NAME));
    private static final BillDTO A_BILL_DTO = new BillDTO(ORDER.items(), AMOUNT, AMOUNT, AMOUNT, AMOUNT);
    private static final OrderDTO A_ORDER_DTO = new OrderDTO(new ArrayList<>());
    private static final Coffee A_COFFEE = new Coffee(new CoffeeType("Latte"), new Amount(2.95f),
        new Recipe(Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Milk, new Quantity(50))));
    private static final List<Coffee> LIST_OF_A_COFFEE = List.of(A_COFFEE);
    private static final List<CoffeeType> LIST_OF_A_COFFEE_TYPE = List.of(new CoffeeType("Latte"));
    private static final List<String> LIST_OF_A_COFFEE_STRING = List.of("Latte");

    private CustomerResource customerResource;
    private CustomerService customerService;
    private MenuService menuService;

    @BeforeEach
    public void createCustomerResource() {
        customerService = mock(CustomerService.class);
        menuService = mock(MenuService.class);
        customerResource = new CustomerResource(customerService, menuService);
    }

    @Test
    public void whenGettingCustomer_shouldGetCustomer() {
        when(customerService.getCustomer(CUSTOMER_ID)).thenReturn(A_CUSTOMER_DTO);

        customerResource.getCustomer(CUSTOMER_ID.value());

        verify(customerService).getCustomer(CUSTOMER_ID);
    }

    @Test
    public void givenValidCustomerID_whenGettingCustomer_shouldReturn200() {
        when(customerService.getCustomer(CUSTOMER_ID)).thenReturn(A_CUSTOMER_DTO);

        Response response = customerResource.getCustomer(CUSTOMER_ID.value());

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenPuttingOrderForCustomer_shouldPlaceOrderForCustomer() {
        when(menuService.getCoffeesFromCoffeeTypes(LIST_OF_A_COFFEE_TYPE)).thenReturn(LIST_OF_A_COFFEE);
        OrderRequest orderRequest = new OrderRequestFixture().withOrders(LIST_OF_A_COFFEE_STRING).build();
        CustomerOrderParams customerOrderParams = new CustomerOrderParams(CUSTOMER_ID.value(), LIST_OF_A_COFFEE);

        customerResource.putOrderForCustomer(CUSTOMER_ID.value(), orderRequest);

        verify(customerService).placeOrder(customerOrderParams);
    }

    @Test
    public void givenValidRequestAndValidCustomerID_whenPuttingOrderForCustomer_shouldReturn200() {
        OrderRequest orderRequest = new OrderRequestFixture().withOrders(LIST_OF_A_COFFEE_STRING).build();

        Response response = customerResource.putOrderForCustomer(CUSTOMER_ID.value(), orderRequest);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenGettingCustomerBill_shouldGetCustomerBill() {
        when(customerService.getCustomerBill(CUSTOMER_ID)).thenReturn(A_BILL_DTO);

        customerResource.getCustomerBill(CUSTOMER_ID.value());

        verify(customerService).getCustomerBill(CUSTOMER_ID);
    }

    @Test
    public void givenValidCustomerID_whenGettingBill_shouldReturn200() {
        when(customerService.getCustomerBill(CUSTOMER_ID)).thenReturn(A_BILL_DTO);

        Response response = customerResource.getCustomerBill(CUSTOMER_ID.value());

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenGettingOrders_shouldGetOrders() {
        when(customerService.getOrder(CUSTOMER_ID)).thenReturn(A_ORDER_DTO);

        customerResource.getOrders(CUSTOMER_ID.value());

        verify(customerService).getOrder(CUSTOMER_ID);
    }

    @Test
    public void givenValidCustomerID_whenGettingOrders_shouldReturn200() {
        when(customerService.getOrder(CUSTOMER_ID)).thenReturn(A_ORDER_DTO);

        Response response = customerResource.getOrders(CUSTOMER_ID.value());

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
