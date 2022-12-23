package ca.ulaval.glo4002.cafe.small.cafe.api.customer.assembler;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.customer.assembler.OrdersResponseAssembler;
import ca.ulaval.glo4002.cafe.api.customer.response.OrdersResponse;
import ca.ulaval.glo4002.cafe.application.customer.dto.OrderDTO;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Recipe;
import ca.ulaval.glo4002.cafe.fixture.OrderFixture;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrdersResponseAssemblerTest {
    private static final Coffee A_COFFEE = new Coffee(new CoffeeType("Latte"), new Amount(2.95f),
        new Recipe(List.of(new Ingredient(IngredientType.Espresso, new Quantity(50)), new Ingredient(IngredientType.Milk, new Quantity(50)))));
    private static final Coffee ANOTHER_COFFEE = new Coffee(new CoffeeType("Americano"), new Amount(2.25f),
        new Recipe(List.of(new Ingredient(IngredientType.Espresso, new Quantity(50)), new Ingredient(IngredientType.Water, new Quantity(50)))));

    private OrdersResponseAssembler ordersResponseAssembler;

    @BeforeEach
    public void createAssembler() {
        ordersResponseAssembler = new OrdersResponseAssembler();
    }

    @Test
    public void whenAssemblingOrdersResponse_shouldReturnOrdersResponseWithItemsInRightOrder() {
        OrderDTO orderDTO = OrderDTO.fromOrder(new OrderFixture().withItems(List.of(A_COFFEE, ANOTHER_COFFEE)).build());

        OrdersResponse actualOrderResponse = ordersResponseAssembler.toOrdersResponse(orderDTO);

        assertEquals(List.of("Latte", "Americano"), actualOrderResponse.orders());
    }
}
