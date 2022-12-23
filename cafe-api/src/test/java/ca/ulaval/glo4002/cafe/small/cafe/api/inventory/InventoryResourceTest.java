package ca.ulaval.glo4002.cafe.small.cafe.api.inventory;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.inventory.InventoryResource;
import ca.ulaval.glo4002.cafe.api.request.IngredientsRequest;
import ca.ulaval.glo4002.cafe.application.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.application.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.application.parameter.IngredientsParams;
import ca.ulaval.glo4002.cafe.fixture.request.IngredientsRequestFixture;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InventoryResourceTest {
    private static final InventoryDTO A_INVENTORY_DTO = new InventoryDTO(new HashMap<>());
    private static final int CHOCOLATE = 1;
    private static final int ESPRESSO = 1;
    private static final int MILK = 1;
    private static final int WATER = 1;

    private InventoryService inventoryService;
    private InventoryResource inventoryResource;

    @BeforeEach
    public void createInventoryResource() {
        inventoryService = mock(InventoryService.class);
        inventoryResource = new InventoryResource(inventoryService);
    }

    @Test
    public void givenValidRequest_whenAddingIngredients_shouldAddIngredientsToInventory() {
        IngredientsRequest ingredientsRequest =
            new IngredientsRequestFixture().withChocolate(CHOCOLATE).withMilk(MILK).withEspresso(ESPRESSO).withWater(WATER).build();
        IngredientsParams ingredientsParams = new IngredientsParams(CHOCOLATE, MILK, WATER, ESPRESSO);

        inventoryResource.putInventory(ingredientsRequest);

        verify(inventoryService).addIngredientsToInventory(ingredientsParams);
    }

    @Test
    public void givenValidRequest_whenAddingIngredients_shouldReturn200() {
        IngredientsRequest ingredientsRequest =
            new IngredientsRequestFixture().withChocolate(CHOCOLATE).withMilk(MILK).withEspresso(ESPRESSO).withWater(WATER).build();

        Response response = inventoryResource.putInventory(ingredientsRequest);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenGettingInventory_shouldGetInventory() {
        when(inventoryService.getInventory()).thenReturn(A_INVENTORY_DTO);

        inventoryResource.getInventory();

        verify(inventoryService).getInventory();
    }

    @Test
    public void whenGettingInventory_shouldReturn200() {
        when(inventoryService.getInventory()).thenReturn(A_INVENTORY_DTO);

        Response response = inventoryResource.getInventory();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
