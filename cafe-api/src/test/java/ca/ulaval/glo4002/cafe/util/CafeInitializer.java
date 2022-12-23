package ca.ulaval.glo4002.cafe.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.billing.TipRate;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeFactory;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Recipe;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.taxing.CountryTax;
import ca.ulaval.glo4002.cafe.domain.taxing.Location;
import ca.ulaval.glo4002.cafe.domain.valueobjects.Amount;
import ca.ulaval.glo4002.cafe.domain.valueobjects.CafeName;

public class CafeInitializer {
    private static final CafeName CAFE_NAME = new CafeName("Les 4-Fées");
    private static final CubeSize CUBE_SIZE = new CubeSize(4);
    private static final List<CubeName> CUBE_NAMES =
        List.of(new CubeName("Wanda"), new CubeName("Tinker Bell"), new CubeName("Bloom"), new CubeName("Merryweather"));
    private static final ReservationType RESERVATION_STRATEGY_TYPE = ReservationType.Default;
    private static final TipRate GROUP_TIP_RATE = new TipRate(0);
    private static final Location LOCATION = new Location(CountryTax.None, Optional.empty(), Optional.empty());

    public void initializeCafe(CafeRepository cafeRepository) {
        CafeFactory cafeFactory = new CafeFactory();
        Cafe cafe = cafeFactory.createCafe(CUBE_SIZE, CAFE_NAME, RESERVATION_STRATEGY_TYPE, LOCATION, GROUP_TIP_RATE, CUBE_NAMES, getDefaultCoffees());
        cafeRepository.saveOrUpdate(cafe);
    }

    private List<Coffee> getDefaultCoffees() {
        CoffeeFactory coffeeFactory = new CoffeeFactory();
        return new ArrayList<>(Arrays.asList(coffeeFactory.createCoffee(new CoffeeType("Americano"), new Amount(2.25f),
                new Recipe(Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Water, new Quantity(50)))),
            coffeeFactory.createCoffee(new CoffeeType("Dark Roast"), new Amount(2.10f), new Recipe(
                Map.of(IngredientType.Espresso, new Quantity(40), IngredientType.Water, new Quantity(40), IngredientType.Chocolate, new Quantity(10),
                    IngredientType.Milk, new Quantity(10)))), coffeeFactory.createCoffee(new CoffeeType("Cappuccino"), new Amount(3.29f),
                new Recipe(Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Water, new Quantity(40), IngredientType.Milk, new Quantity(10)))),
            coffeeFactory.createCoffee(new CoffeeType("Espresso"), new Amount(2.95f), new Recipe(Map.of(IngredientType.Espresso, new Quantity(60)))),
            coffeeFactory.createCoffee(new CoffeeType("Flat White"), new Amount(3.75f),
                new Recipe(Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Milk, new Quantity(50)))),
            coffeeFactory.createCoffee(new CoffeeType("Latte"), new Amount(2.95f),
                new Recipe(Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Milk, new Quantity(50)))),
            coffeeFactory.createCoffee(new CoffeeType("Macchiato"), new Amount(4.75f),
                new Recipe(Map.of(IngredientType.Espresso, new Quantity(80), IngredientType.Milk, new Quantity(20)))),
            coffeeFactory.createCoffee(new CoffeeType("Mocha"), new Amount(4.15f), new Recipe(
                Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Milk, new Quantity(40), IngredientType.Chocolate, new Quantity(10))))));
    }
}
