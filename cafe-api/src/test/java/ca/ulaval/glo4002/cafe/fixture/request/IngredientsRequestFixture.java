package ca.ulaval.glo4002.cafe.fixture.request;

import ca.ulaval.glo4002.cafe.api.request.IngredientsRequest;

public class IngredientsRequestFixture {
    public int chocolat = 100;
    public int espresso = 100;
    public int milk = 100;
    public int water = 100;

    public IngredientsRequestFixture withChocolate(int chocolate) {
        this.chocolat = chocolate;
        return this;
    }

    public IngredientsRequestFixture withEspresso(int espresso) {
        this.espresso = espresso;
        return this;
    }

    public IngredientsRequestFixture withMilk(int milk) {
        this.milk = milk;
        return this;
    }

    public IngredientsRequestFixture withWater(int water) {
        this.water = water;
        return this;
    }

    public IngredientsRequest build() {
        IngredientsRequest ingredientsRequest = new IngredientsRequest();
        ingredientsRequest.Chocolate = chocolat;
        ingredientsRequest.Espresso = espresso;
        ingredientsRequest.Milk = milk;
        ingredientsRequest.Water = water;
        return ingredientsRequest;
    }
}
