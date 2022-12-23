package ca.ulaval.glo4002.cafe.fixture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.Cafe;
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
import ca.ulaval.glo4002.cafe.domain.taxing.LocationTax;
import ca.ulaval.glo4002.cafe.domain.taxing.ProvinceTax;
import ca.ulaval.glo4002.cafe.domain.valueobjects.Amount;
import ca.ulaval.glo4002.cafe.domain.valueobjects.CafeConfiguration;
import ca.ulaval.glo4002.cafe.domain.valueobjects.CafeName;

public class CafeFixture {
    private static final CoffeeFactory COFFEE_FACTORY = new CoffeeFactory();
    private static final ReservationType RESERVATION_STRATEGY_TYPE = ReservationType.Default;
    private List<CubeName> cubeNames = List.of(new CubeName("Wanda"), new CubeName("Tinker Bell"), new CubeName("Bloom"), new CubeName("Merryweather"));
    private CafeName name = new CafeName("Les 4-Fées");
    private CubeSize cubeSize = new CubeSize(4);
    private TipRate groupTipRate = new TipRate(0.05f);
    private LocationTax locationTax = new LocationTax(CountryTax.CA, Optional.of(ProvinceTax.AB), Optional.empty());
    private List<Coffee> coffees = getDefaultCoffees();

    public CafeFixture withName(CafeName name) {
        this.name = name;
        return this;
    }

    public CafeFixture withCubeNames(List<CubeName> cubeNames) {
        this.cubeNames = cubeNames;
        return this;
    }

    public CafeFixture withCubeSize(CubeSize cubeSize) {
        this.cubeSize = cubeSize;
        return this;
    }

    public CafeFixture withLocation(LocationTax locationTax) {
        this.locationTax = locationTax;
        return this;
    }

    public CafeFixture withGroupTipRate(TipRate groupTipRate) {
        this.groupTipRate = groupTipRate;
        return this;
    }

    public CafeFixture withCoffees(List<Coffee> coffees) {
        this.coffees = coffees;
        return this;
    }

    public Cafe build() {
        CafeConfiguration cafeConfiguration = new CafeConfiguration(cubeSize, name, RESERVATION_STRATEGY_TYPE, locationTax, groupTipRate);
        return new Cafe(cubeNames, coffees, cafeConfiguration);
    }

    private List<Coffee> getDefaultCoffees() {
        return new ArrayList<>(Arrays.asList(COFFEE_FACTORY.createCoffee(new CoffeeType("Americano"), new Amount(2.25f),
                new Recipe(Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Water, new Quantity(50)))),
            COFFEE_FACTORY.createCoffee(new CoffeeType("Dark Roast"), new Amount(2.10f), new Recipe(
                Map.of(IngredientType.Espresso, new Quantity(40), IngredientType.Water, new Quantity(40), IngredientType.Chocolate, new Quantity(10),
                    IngredientType.Milk, new Quantity(10)))), COFFEE_FACTORY.createCoffee(new CoffeeType("Cappuccino"), new Amount(3.29f),
                new Recipe(Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Water, new Quantity(40), IngredientType.Milk, new Quantity(10)))),
            COFFEE_FACTORY.createCoffee(new CoffeeType("Espresso"), new Amount(2.95f), new Recipe(Map.of(IngredientType.Espresso, new Quantity(60)))),
            COFFEE_FACTORY.createCoffee(new CoffeeType("Flat White"), new Amount(3.75f),
                new Recipe(Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Milk, new Quantity(50)))),
            COFFEE_FACTORY.createCoffee(new CoffeeType("Latte"), new Amount(2.95f),
                new Recipe(Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Milk, new Quantity(50)))),
            COFFEE_FACTORY.createCoffee(new CoffeeType("Macchiato"), new Amount(4.75f),
                new Recipe(Map.of(IngredientType.Espresso, new Quantity(80), IngredientType.Milk, new Quantity(20)))),
            COFFEE_FACTORY.createCoffee(new CoffeeType("Mocha"), new Amount(4.15f), new Recipe(
                Map.of(IngredientType.Espresso, new Quantity(50), IngredientType.Milk, new Quantity(40), IngredientType.Chocolate, new Quantity(10))))));
    }
}
