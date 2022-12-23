package ca.ulaval.glo4002.cafe.small.cafe.application.inventory;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.application.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.application.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.application.parameter.IngredientsParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.fixture.CafeFixture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InventoryServiceTest {
    private static final Cafe A_CAFE = new CafeFixture().build();
    private static final IngredientsParams AN_INGREDIENTS_PARAMS = new IngredientsParams(1, 2, 3, 4);

    private InventoryService inventoryService;
    private CafeRepository cafeRepository;

    @BeforeEach
    public void createInventoryService() {
        cafeRepository = mock(CafeRepository.class);
        inventoryService = new InventoryService(cafeRepository);
    }

    @Test
    public void whenAddingIngredientsToInventory_shouldGetCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        inventoryService.addIngredientsToInventory(AN_INGREDIENTS_PARAMS);

        verify(cafeRepository).get();
    }

    @Test
    public void whenAddingIngredientsToInventory_shouldAddIngredientsInInventory() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        inventoryService.addIngredientsToInventory(AN_INGREDIENTS_PARAMS);

        HashMap<IngredientType, Quantity> ingredients = new HashMap<>();
        ingredients.put(IngredientType.Chocolate, AN_INGREDIENTS_PARAMS.ingredients().get(IngredientType.Chocolate));
        ingredients.put(IngredientType.Milk, AN_INGREDIENTS_PARAMS.ingredients().get(IngredientType.Milk));
        ingredients.put(IngredientType.Water, AN_INGREDIENTS_PARAMS.ingredients().get(IngredientType.Water));
        ingredients.put(IngredientType.Espresso, AN_INGREDIENTS_PARAMS.ingredients().get(IngredientType.Espresso));
        verify(mockCafe).addIngredientsToInventory(ingredients);
    }

    @Test
    public void whenAddingIngredientsToInventory_shouldUpdateCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        inventoryService.addIngredientsToInventory(AN_INGREDIENTS_PARAMS);

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }

    @Test
    public void whenGettingInventory_shouldGetCafe() {
        when(cafeRepository.get()).thenReturn(A_CAFE);

        inventoryService.getInventory();

        verify(cafeRepository).get();
    }

    @Test
    public void whenGettingInventory_shouldReturnInventoryDTO() {
        when(cafeRepository.get()).thenReturn(A_CAFE);
        InventoryDTO expectedInventoryDTO = new InventoryDTO(new HashMap<>());

        InventoryDTO actualInventoryDTO = inventoryService.getInventory();

        assertEquals(expectedInventoryDTO, actualInventoryDTO);
    }
}
