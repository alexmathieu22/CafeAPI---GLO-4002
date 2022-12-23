package ca.ulaval.glo4002.cafe.medium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.application.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.application.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.application.parameter.IngredientsParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.infrastructure.InMemoryCafeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryServiceTest {
    private static final IngredientsParams INGREDIENT_PARAMS = new IngredientsParams(25, 20, 15, 10);

    private InventoryService inventoryService;
    private CafeRepository cafeRepository;

    private void initializeCafe(CafeFactory cafeFactory, CafeRepository cafeRepository) {
        Cafe cafe = cafeFactory.createCafe();
        cafeRepository.saveOrUpdate(cafe);
    }

    @BeforeEach
    public void instanciateAttributes() {
        CafeFactory cafeFactory = new CafeFactory();
        cafeRepository = new InMemoryCafeRepository();
        inventoryService = new InventoryService(cafeRepository);
        initializeCafe(cafeFactory, cafeRepository);
    }

    @Test
    public void whenAddingIngredientsToInventory_shouldAddIngredientsToInventory() {
        inventoryService.addIngredientsToInventory(INGREDIENT_PARAMS);

        InventoryDTO inventory = inventoryService.getInventory();
        assertEquals(INGREDIENT_PARAMS.ingredients().get(IngredientType.Chocolate), inventory.ingredients().get(IngredientType.Chocolate));
        assertEquals(INGREDIENT_PARAMS.ingredients().get(IngredientType.Milk), inventory.ingredients().get(IngredientType.Milk));
        assertEquals(INGREDIENT_PARAMS.ingredients().get(IngredientType.Water), inventory.ingredients().get(IngredientType.Water));
        assertEquals(INGREDIENT_PARAMS.ingredients().get(IngredientType.Espresso), inventory.ingredients().get(IngredientType.Espresso));
    }
}
