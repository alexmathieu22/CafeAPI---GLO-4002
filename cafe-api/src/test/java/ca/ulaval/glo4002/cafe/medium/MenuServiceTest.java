package ca.ulaval.glo4002.cafe.medium;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.application.menu.MenuService;
import ca.ulaval.glo4002.cafe.application.menu.parameter.NewCoffeeParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeFactory;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Recipe;
import ca.ulaval.glo4002.cafe.infrastructure.InMemoryCafeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MenuServiceTest {
    private static final Coffee A_COFFEE = new Coffee(new CoffeeType("NewCoffee"), new Amount(2.95f),
        new Recipe(List.of(new Ingredient(IngredientType.Espresso, new Quantity(50)), new Ingredient(IngredientType.Milk, new Quantity(50)))));
    private static final NewCoffeeParams A_NEW_COFFEE_PARAMS = NewCoffeeParams.from("NewCoffee", 2.95f, 0, 50, 0, 50);

    private MenuService menuService;
    private CafeRepository cafeRepository;
    private CoffeeFactory coffeeFactory;

    private void initializeCafe(CafeRepository cafeRepository) {
        CafeFactory cafeFactory = new CafeFactory();
        Cafe cafe = cafeFactory.createCafe();
        cafeRepository.saveOrUpdate(cafe);
    }

    @BeforeEach
    public void instanciateAttributes() {
        coffeeFactory = mock(CoffeeFactory.class);
        cafeRepository = new InMemoryCafeRepository();
        menuService = new MenuService(cafeRepository, coffeeFactory);
        initializeCafe(cafeRepository);
    }

    @Test
    public void whenAddingCoffeeToMenu_shouldAddCoffeeToMenu() {
        when(coffeeFactory.createCoffee(any(), any(), any())).thenReturn(A_COFFEE);

        menuService.addCoffeeToMenu(A_NEW_COFFEE_PARAMS);

        Cafe cafe = cafeRepository.get();
        assertEquals(A_COFFEE, cafe.getCoffeeByCoffeeType(A_COFFEE.coffeeType()));
    }
}
