package ca.ulaval.glo4002.cafe.api.menu.request;

import ca.ulaval.glo4002.cafe.api.request.IngredientsRequest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class NewCoffeeRequest {
    @NotNull(message = "The coffee name may not be null")
    public String name;

    @NotNull(message = "The list of ingredients may not be null")
    public IngredientsRequest ingredients;

    @NotNull(message = "The cost may not be null")
    @Min(value = 0, message = "Cost must be greater than zero")
    public float cost;
}
