package ca.ulaval.glo4002.cafe.api.customer;

import java.util.List;

import ca.ulaval.glo4002.cafe.api.customer.assembler.BillResponseAssembler;
import ca.ulaval.glo4002.cafe.api.customer.assembler.CustomerResponseAssembler;
import ca.ulaval.glo4002.cafe.api.customer.assembler.OrdersResponseAssembler;
import ca.ulaval.glo4002.cafe.api.customer.request.OrderRequest;
import ca.ulaval.glo4002.cafe.api.customer.response.BillResponse;
import ca.ulaval.glo4002.cafe.api.customer.response.CustomerResponse;
import ca.ulaval.glo4002.cafe.api.customer.response.OrdersResponse;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.customer.parameter.CustomerOrderParams;
import ca.ulaval.glo4002.cafe.application.menu.MenuService;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Order;

import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {
    private final CustomerService customersService;
    private final MenuService menuService;
    private final CustomerResponseAssembler customerResponseAssembler = new CustomerResponseAssembler();
    private final BillResponseAssembler billResponseAssembler = new BillResponseAssembler();
    private final OrdersResponseAssembler ordersResponseAssembler = new OrdersResponseAssembler();

    public CustomerResource(CustomerService customersService, MenuService menuService) {
        this.customersService = customersService;
        this.menuService = menuService;
    }

    @GET
    @Path("/{customerId}")
    public Response getCustomer(@PathParam("customerId") String customerId) {
        CustomerResponse customerResponse = customerResponseAssembler.toCustomerResponse(customersService.getCustomer(new CustomerId(customerId)));
        return Response.ok(customerResponse).build();
    }

    @PUT
    @Path("/{customerId}/orders")
    public Response putOrderForCustomer(@PathParam("customerId") String customerId, @Valid OrderRequest orderRequest) {
        List<CoffeeType> coffeeTypesOrder = orderRequest.orders.stream().map(CoffeeType::new).toList();
        List<Coffee> coffees = menuService.getCoffeesFromCoffeeTypes(coffeeTypesOrder);
        CustomerOrderParams customerOrderParams = new CustomerOrderParams(customerId, coffees);

        customersService.placeOrder(customerOrderParams);
        return Response.ok().build();
    }

    @GET
    @Path("/{customerId}/orders")
    public Response getOrders(@PathParam("customerId") String customerId) {
        OrdersResponse ordersResponse = ordersResponseAssembler.toOrdersResponse(customersService.getOrder(new CustomerId(customerId)));
        return Response.ok(ordersResponse).build();
    }

    @GET
    @Path("/{customerId}/bill")
    public Response getCustomerBill(@PathParam("customerId") String customerId) {
        BillResponse billResponse = billResponseAssembler.toBillResponse(customersService.getCustomerBill(new CustomerId(customerId)));
        return Response.ok(billResponse).build();
    }
}
