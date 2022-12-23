package ca.ulaval.glo4002.cafe.application.inventory;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.cafe.application.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.application.parameter.IngredientsParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;

public class InventoryService {
    private final CafeRepository cafeRepository;

    public InventoryService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public InventoryDTO getInventory() {
        Cafe cafe = cafeRepository.get();
        return InventoryDTO.fromInventory(cafe.getInventory());
    }

    public void addIngredientsToInventory(IngredientsParams ingredientsParams) {
        Cafe cafe = cafeRepository.get();
        cafe.addIngredientsToInventory(getIngredientsToAddMap(ingredientsParams));
        cafeRepository.saveOrUpdate(cafe);
    }

    private Map<IngredientType, Quantity> getIngredientsToAddMap(IngredientsParams ingredientsParams) {
        Map<IngredientType, Quantity> ingredientsToAdd = new HashMap<>();
        ingredientsToAdd.put(IngredientType.Chocolate, ingredientsParams.ingredients().get(IngredientType.Chocolate));
        ingredientsToAdd.put(IngredientType.Milk, ingredientsParams.ingredients().get(IngredientType.Milk));
        ingredientsToAdd.put(IngredientType.Water, ingredientsParams.ingredients().get(IngredientType.Water));
        ingredientsToAdd.put(IngredientType.Espresso, ingredientsParams.ingredients().get(IngredientType.Espresso));
        return ingredientsToAdd;
    }
}
