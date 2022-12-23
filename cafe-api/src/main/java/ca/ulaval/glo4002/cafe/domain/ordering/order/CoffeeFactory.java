package ca.ulaval.glo4002.cafe.domain.ordering.order;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;

public class CoffeeFactory {
    public Coffee createCoffee(CoffeeType coffeeType, Amount price, Recipe recipe) {
        return new Coffee(coffeeType, price, recipe);
    }
}
