package ca.ulaval.glo4002.cafe.domain.inventory;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.cafe.domain.exception.InsufficientIngredientsException;

public class Inventory {
    private HashMap<IngredientType, Quantity> ingredients = new HashMap<>();

    public HashMap<IngredientType, Quantity> getIngredients() {
        return ingredients;
    }

    public void clear() {
        ingredients = new HashMap<>();
    }

    public void add(Map<IngredientType, Quantity> newIngredients) {
        newIngredients.forEach((ingredientType, quantity) -> {
            ingredients.put(ingredientType, ingredients.containsKey(ingredientType) ? ingredients.get(ingredientType).add(quantity) : quantity);
        });
    }

    public void useIngredients(Map<IngredientType, Quantity> ingredients) {
        validateIfEnoughIngredients(ingredients);
        removeIngredients(ingredients);
    }

    private void validateIfEnoughIngredients(Map<IngredientType, Quantity> ingredientsNeeded) {
        ingredientsNeeded.forEach((type, quantityNeeded) -> {
            if (!ingredients.containsKey(type) || quantityNeeded.isGreaterThan(ingredients.get(type))) {
                throw new InsufficientIngredientsException();
            }
        });
    }

    private void removeIngredients(Map<IngredientType, Quantity> ingredientsToRemove) {
        ingredientsToRemove.forEach((type, quantityToRemove) -> {
            ingredients.put(type, ingredients.get(type).remove(quantityToRemove));
        });
    }
}
