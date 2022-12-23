package ca.ulaval.glo4002.cafe.application.parameter;

import java.util.Map;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;

public record IngredientsParams(Map<IngredientType, Quantity> ingredients) {
    public IngredientsParams(int chocolate, int milk, int water, int espresso) {
        this(Map.of(IngredientType.Chocolate, new Quantity(chocolate), IngredientType.Milk, new Quantity(milk),
            IngredientType.Water, new Quantity(water), IngredientType.Espresso, new Quantity(espresso)));
    }

    public static IngredientsParams from(int chocolate, int milk, int water, int espresso) {
        return new IngredientsParams(chocolate, milk, water, espresso);
    }
}
