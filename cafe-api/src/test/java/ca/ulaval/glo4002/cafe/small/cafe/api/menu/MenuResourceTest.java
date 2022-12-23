package ca.ulaval.glo4002.cafe.small.cafe.api.menu;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.menu.MenuResource;
import ca.ulaval.glo4002.cafe.api.menu.request.NewCoffeeRequest;
import ca.ulaval.glo4002.cafe.api.request.IngredientsRequest;
import ca.ulaval.glo4002.cafe.application.menu.MenuService;
import ca.ulaval.glo4002.cafe.application.menu.parameter.NewCoffeeParams;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Recipe;
import ca.ulaval.glo4002.cafe.domain.valueobjects.Amount;
import ca.ulaval.glo4002.cafe.fixture.request.IngredientsRequestFixture;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MenuResourceTest {
    private static final int CHOCOLATE = 1;
    private static final int ESPRESSO = 1;
    private static final int MILK = 1;
    private static final int WATER = 1;
    private static final String A_COFFEE_NAME = "name";
    private static final IngredientsRequest AN_INGREDIENTS_REQUEST =
        new IngredientsRequestFixture().withChocolate(CHOCOLATE).withMilk(MILK).withEspresso(ESPRESSO).withWater(WATER).build();
    private static final float A_COST = 1.0f;

    private MenuService menuService;
    private MenuResource menuResource;

    @BeforeEach
    public void createInventoryResource() {
        menuService = mock(MenuService.class);
        menuResource = new MenuResource(menuService);
    }

    @Test
    public void givenValidNewCoffeeRequest_whenAddingCoffeeToMenu_shouldCallMenuService() {
        NewCoffeeRequest newCoffeeRequest = new NewCoffeeRequest();
        newCoffeeRequest.name = A_COFFEE_NAME;
        newCoffeeRequest.ingredients = AN_INGREDIENTS_REQUEST;
        newCoffeeRequest.cost = A_COST;
        NewCoffeeParams newCoffeeParams = new NewCoffeeParams(new CoffeeType(A_COFFEE_NAME), new Amount(A_COST),
            new Recipe(Map.of(IngredientType.Chocolate, new Quantity(CHOCOLATE), IngredientType.Milk, new Quantity(MILK),
                IngredientType.Water, new Quantity(WATER), IngredientType.Espresso, new Quantity(ESPRESSO))));

        menuResource.addCoffeeToMenu(newCoffeeRequest);

        verify(menuService).addCoffeeToMenu(newCoffeeParams);
    }

    @Test
    public void givenValidRequest_whenAddingIngredients_shouldReturn200() {
        NewCoffeeRequest newCoffeeRequest = new NewCoffeeRequest();
        newCoffeeRequest.name = A_COFFEE_NAME;
        newCoffeeRequest.ingredients = AN_INGREDIENTS_REQUEST;
        newCoffeeRequest.cost = A_COST;

        Response response = menuResource.addCoffeeToMenu(newCoffeeRequest);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
