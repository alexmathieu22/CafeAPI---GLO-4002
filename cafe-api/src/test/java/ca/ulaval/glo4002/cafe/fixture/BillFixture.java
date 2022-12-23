package ca.ulaval.glo4002.cafe.fixture;

import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.cafe.domain.billing.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Order;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Recipe;

public class BillFixture {
    private static final Coffee A_COFFEE = new Coffee(new CoffeeType("Latte"), new Amount(2.95f),
        new Recipe(Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Milk, new Quantity(50))));
    private static final Coffee ANOTHER_COFFEE = new Coffee(new CoffeeType("Americano"), new Amount(2.25f),
        new Recipe(Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Water, new Quantity(50))));
    private Order coffeeOrder =
        new Order(List.of(A_COFFEE, ANOTHER_COFFEE));
    private Amount subtotal = new Amount(10.0f);
    private Amount taxes = new Amount(1.0f);
    private Amount tip = new Amount(2.0f);

    public BillFixture withCoffeeOrder(Order coffeeOrder) {
        this.coffeeOrder = coffeeOrder;
        return this;
    }

    public BillFixture withSubtotal(Amount subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    public BillFixture withTaxes(Amount taxes) {
        this.taxes = taxes;
        return this;
    }

    public BillFixture withTip(Amount tip) {
        this.tip = tip;
        return this;
    }

    public Bill build() {
        return new Bill(coffeeOrder, subtotal, taxes, tip);
    }
}
