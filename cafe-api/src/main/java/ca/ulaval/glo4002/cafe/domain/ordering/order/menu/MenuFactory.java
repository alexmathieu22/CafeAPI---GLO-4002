package ca.ulaval.glo4002.cafe.domain.ordering.order.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeFactory;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Recipe;
import ca.ulaval.glo4002.cafe.domain.valueobjects.Amount;

public class MenuFactory {
    private static final CoffeeFactory COFFEE_FACTORY = new CoffeeFactory();

    public Menu createMenu() {
        return new Menu(getDefaultCoffees());
    }

    public List<Coffee> getDefaultCoffees() {
        return new ArrayList<>(Arrays.asList(
            COFFEE_FACTORY.createCoffee(new CoffeeType("Americano"), new Amount(2.25f), new Recipe(Map.of(
                IngredientType.Espresso, new Quantity(50),
                IngredientType.Water, new Quantity(50)))),
            COFFEE_FACTORY.createCoffee(new CoffeeType("Dark Roast"), new Amount(2.10f), new Recipe(Map.of(
                IngredientType.Espresso, new Quantity(40),
                IngredientType.Water, new Quantity(40),
                IngredientType.Chocolate, new Quantity(10),
                IngredientType.Milk, new Quantity(10)))),
            COFFEE_FACTORY.createCoffee(new CoffeeType("Cappuccino"), new Amount(3.29f), new Recipe(Map.of(
                IngredientType.Espresso, new Quantity(50),
                IngredientType.Water, new Quantity(40),
                IngredientType.Milk, new Quantity(10)))),
            COFFEE_FACTORY.createCoffee(new CoffeeType("Espresso"), new Amount(2.95f), new Recipe(Map.of(
                IngredientType.Espresso, new Quantity(60)))),
            COFFEE_FACTORY.createCoffee(new CoffeeType("Flat White"), new Amount(3.75f), new Recipe(Map.of(
                IngredientType.Espresso, new Quantity(50),
                IngredientType.Milk, new Quantity(50)))),
            COFFEE_FACTORY.createCoffee(new CoffeeType("Latte"), new Amount(2.95f), new Recipe(Map.of(
                IngredientType.Espresso, new Quantity(50),
                IngredientType.Milk, new Quantity(50)))),
            COFFEE_FACTORY.createCoffee(new CoffeeType("Macchiato"), new Amount(4.75f), new Recipe(Map.of(
                IngredientType.Espresso, new Quantity(80),
                IngredientType.Milk, new Quantity(20)))),
            COFFEE_FACTORY.createCoffee(new CoffeeType("Mocha"), new Amount(4.15f), new Recipe(Map.of(
                IngredientType.Espresso, new Quantity(50),
                IngredientType.Milk, new Quantity(40),
                IngredientType.Chocolate, new Quantity(10))))));
    }
}
