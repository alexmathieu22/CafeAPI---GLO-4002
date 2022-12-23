package ca.ulaval.glo4002.cafe.small.cafe.api.customer.assembler;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.customer.assembler.BillResponseAssembler;
import ca.ulaval.glo4002.cafe.api.customer.response.BillResponse;
import ca.ulaval.glo4002.cafe.application.customer.dto.BillDTO;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Order;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Recipe;
import ca.ulaval.glo4002.cafe.fixture.BillFixture;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillResponseAssemblerTest {
    private static final Coffee A_COFFEE = new Coffee(new CoffeeType("Latte"), new Amount(2.95f),
        new Recipe(Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Milk, new Quantity(50))));
    private static final Coffee ANOTHER_COFFEE = new Coffee(new CoffeeType("Americano"), new Amount(2.25f),
        new Recipe(Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Water, new Quantity(50))));

    private static final Order A_COFFEE_ORDER = new Order(List.of(A_COFFEE, ANOTHER_COFFEE));

    private BillResponseAssembler billResponseAssembler;

    @BeforeEach
    public void createAssembler() {
        billResponseAssembler = new BillResponseAssembler();
    }

    @Test
    public void givenBillDTO_whenAssemblingBillResponse_shouldAssembleBillResponseWithCoffeeTypeListInSameOrder() {
        BillDTO billDTO = BillDTO.fromBill(new BillFixture().withCoffeeOrder(A_COFFEE_ORDER).build());

        BillResponse actualBillResponse = billResponseAssembler.toBillResponse(billDTO);

        assertEquals(actualBillResponse.orders(), A_COFFEE_ORDER.items().stream().map(coffee -> coffee.coffeeType().value()).toList());
    }

    @Test
    public void givenAmountWithMoreThanTwoDecimal_whenAssemblingBillResponse_shouldAssembleBillAmountsRoundedUp() {
        Amount anAmountWithMoreThanTwoDecimal = new Amount(4.91001f);
        BillDTO billDTO = BillDTO.fromBill(new BillFixture().withSubtotal(anAmountWithMoreThanTwoDecimal).build());

        BillResponse actualBillResponse = billResponseAssembler.toBillResponse(billDTO);

        assertEquals(4.92f, actualBillResponse.subtotal());
    }
}
