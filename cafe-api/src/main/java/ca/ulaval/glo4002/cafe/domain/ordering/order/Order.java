package ca.ulaval.glo4002.cafe.domain.ordering.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;

public record Order(List<Coffee> items) {
    public Order addAll(Order otherOrder) {
        return new Order(Stream.concat(items().stream(), otherOrder.items().stream()).toList());
    }

    public Map<IngredientType, Quantity> ingredientsNeeded() {
        Map<IngredientType, Quantity> ingredientsNeeded = new HashMap<>();
        for (Coffee coffee : items()) {
            coffee.recipe().ingredients().forEach((ingredientType, quantity) -> {
                ingredientsNeeded.put(ingredientType,
                    ingredientsNeeded.containsKey(ingredientType) ? ingredientsNeeded.get(ingredientType).add(quantity) : quantity);
            });
        }
        return ingredientsNeeded;
    }
}
