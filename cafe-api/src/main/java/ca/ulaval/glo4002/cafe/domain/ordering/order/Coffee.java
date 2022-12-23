package ca.ulaval.glo4002.cafe.domain.ordering.order;

import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;

public record Coffee(CoffeeType coffeeType, Amount price, Recipe recipe) {
}
