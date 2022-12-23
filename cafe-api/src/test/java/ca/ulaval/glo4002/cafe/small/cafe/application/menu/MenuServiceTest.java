package ca.ulaval.glo4002.cafe.small.cafe.application.menu;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.application.menu.MenuService;
import ca.ulaval.glo4002.cafe.application.menu.parameter.NewCoffeeParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeFactory;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MenuServiceTest {
    private static final Coffee A_COFFEE = new Coffee(new CoffeeType("Latte"), new Amount(2.95f),
        new Recipe(List.of(new Ingredient(IngredientType.Espresso, new Quantity(50)), new Ingredient(IngredientType.Milk, new Quantity(50)))));
    private static final List<CoffeeType> A_COFFEE_TYPE_LIST = List.of(A_COFFEE.coffeeType());
    private static final NewCoffeeParams A_NEW_COFFEE_PARAMS = NewCoffeeParams.from("Latte", 2.95f, 0, 50, 0, 50);

    private MenuService menuService;
    private CafeRepository cafeRepository;
    private CoffeeFactory coffeeFactory;
    private Cafe cafe;


    @BeforeEach
    public void instanciateAttributes() {
        cafeRepository = mock(CafeRepository.class);
        cafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(cafe);
        coffeeFactory = mock(CoffeeFactory.class);
        menuService = new MenuService(cafeRepository, coffeeFactory);
    }

    @Test
    public void whenAddingCoffeeToMenu_shouldGetCafe() {
        menuService.addCoffeeToMenu(A_NEW_COFFEE_PARAMS);

        verify(cafeRepository).get();
    }

    @Test
    public void whenAddingCoffeeToMenu_shouldAddCoffeeToMenu() {
        when(coffeeFactory.createCoffee(any(), any(), any())).thenReturn(A_COFFEE);

        menuService.addCoffeeToMenu(A_NEW_COFFEE_PARAMS);

        verify(cafe).addCoffeeToMenu(A_COFFEE);
    }

    @Test
    public void whenAddingCoffeeToMenu_shouldUpdateCafe() {
        menuService.addCoffeeToMenu(A_NEW_COFFEE_PARAMS);

        verify(cafeRepository).saveOrUpdate(cafe);
    }

    @Test
    public void whenGettingCoffeesFromCoffeeType_shouldGetCafe() {
        menuService.getCoffeesFromCoffeeTypes(A_COFFEE_TYPE_LIST);

        verify(cafeRepository).get();
    }

    @Test
    public void givenMenuWithCoffee_whenGettingCoffeesFromCoffeeType_shouldGetCoffeeByCoffeeType() {
        menuService.getCoffeesFromCoffeeTypes(A_COFFEE_TYPE_LIST);

        verify(cafe).getCoffeeByCoffeeType(A_COFFEE.coffeeType());
    }

    @Test
    public void givenMenuWithCoffee_whenGettingCoffeesFromCoffeeType_shouldReturnCorrectCoffee() {
        when(cafe.getCoffeeByCoffeeType(any())).thenReturn(A_COFFEE);
        List<Coffee> actualCoffees = menuService.getCoffeesFromCoffeeTypes(List.of(A_COFFEE.coffeeType()));

        assertEquals(List.of(A_COFFEE), actualCoffees);
    }
}
