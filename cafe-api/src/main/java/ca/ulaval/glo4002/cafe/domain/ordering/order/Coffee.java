package ca.ulaval.glo4002.cafe.domain.ordering.order;

import ca.ulaval.glo4002.cafe.domain.valueobjects.Amount;

public record Coffee(CoffeeType coffeeType, Amount price, Recipe recipe) {
}
