package ca.ulaval.glo4002.cafe.application.menu.parameter;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Recipe;

public record NewCoffeeParams(CoffeeType coffeeType, Amount price, Recipe recipe) {
    public static NewCoffeeParams from(String coffeeType, float cost, int chocolate, int milk, int water, int espresso) {
        return new NewCoffeeParams(new CoffeeType(coffeeType), new Amount(cost), new Recipe(
            List.of(new Ingredient(IngredientType.Chocolate, new Quantity(chocolate)), new Ingredient(IngredientType.Milk, new Quantity(milk)),
                new Ingredient(IngredientType.Water, new Quantity(water)), new Ingredient(IngredientType.Espresso, new Quantity(espresso)))));
    }
}
