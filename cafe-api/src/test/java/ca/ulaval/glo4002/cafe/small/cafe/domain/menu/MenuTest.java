package ca.ulaval.glo4002.cafe.small.cafe.domain.menu;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.DuplicateCoffeeNameException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidMenuOrderException;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Menu;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Recipe;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {
    private static final Coffee A_COFFEE = new Coffee(new CoffeeType("Latte"), new Amount(2.95f),
        new Recipe(List.of(new Ingredient(IngredientType.Espresso, new Quantity(50)), new Ingredient(IngredientType.Milk, new Quantity(50)))));

    private final List<Coffee> emptyListOfCoffees = new ArrayList<>();
    private final List<Coffee> listWithOneCoffee = new ArrayList<>(List.of(A_COFFEE));
    private Menu menu;

    @Test
    public void givenMenuWithACoffee_whenGettingCoffeeByCoffeeType_shouldReturnCorrectCoffee() {
        menu = new Menu(listWithOneCoffee);

        Coffee coffee = menu.getCoffeeByCoffeeType(new CoffeeType("Latte"));

        assertEquals(A_COFFEE, coffee);
    }

    @Test
    public void whenGettingCoffeeNotInMenu_shouldThrowInvalidMenuOrderException() {
        menu = new Menu(List.of());

        assertThrows(InvalidMenuOrderException.class, () -> menu.getCoffeeByCoffeeType(new CoffeeType("Latte")));
    }

    @Test
    public void whenAddingCoffeeToMenu_shouldAddCoffeeToMenu() {
        menu = new Menu(emptyListOfCoffees);

        menu.addCoffee(A_COFFEE);

        assertEquals(A_COFFEE, menu.getCoffeeByCoffeeType(A_COFFEE.coffeeType()));
    }

    @Test
    public void whenAddingCoffeeWithExistingCoffeeNameToMenu_shouldThrowDuplicateCoffeeNameException() {
        menu = new Menu(listWithOneCoffee);

        assertThrows(DuplicateCoffeeNameException.class, () -> menu.addCoffee(A_COFFEE));
    }

    @Test
    public void givenNonEmptyMenu_whenClearing_shouldKeepDefaultCoffees() {
        menu = new Menu(listWithOneCoffee);

        menu.clear();

        assertNotNull(menu.getCoffeeByCoffeeType(A_COFFEE.coffeeType()));
    }

    @Test
    public void givenMenuWithAddedCoffee_whenClearing_shouldRemoveIt() {
        menu = new Menu(emptyListOfCoffees);
        menu.addCoffee(A_COFFEE);

        menu.clear();

//        if the coffee was not removed, this would throw an exception
        menu.addCoffee(A_COFFEE);
    }
}
