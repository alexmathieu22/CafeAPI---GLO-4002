package ca.ulaval.glo4002.cafe.application.dto;

import java.util.HashMap;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;

public record InventoryDTO(HashMap<IngredientType, Quantity> ingredients) {
    public static InventoryDTO fromInventory(Inventory inventory) {
        return new InventoryDTO(inventory.getIngredients());
    }
}
