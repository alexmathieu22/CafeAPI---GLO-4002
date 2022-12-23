package ca.ulaval.glo4002.cafe.application.menu;

import java.util.List;

import ca.ulaval.glo4002.cafe.application.menu.parameter.NewCoffeeParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeFactory;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeType;

public class MenuService {
    private final CafeRepository cafeRepository;
    private final CoffeeFactory coffeeFactory;

    public MenuService(CafeRepository cafeRepository, CoffeeFactory coffeeFactory) {
        this.cafeRepository = cafeRepository;
        this.coffeeFactory = coffeeFactory;
    }

    public void addCoffeeToMenu(NewCoffeeParams coffeeParams) {
        Cafe cafe = cafeRepository.get();

        Coffee coffee = coffeeFactory.createCoffee(coffeeParams.coffeeType(), coffeeParams.price(), coffeeParams.recipe());
        cafe.addCoffeeToMenu(coffee);

        cafeRepository.saveOrUpdate(cafe);
    }

    public List<Coffee> getCoffeesFromCoffeeTypes(List<CoffeeType> coffeeTypes) {
        Cafe cafe = cafeRepository.get();

        return coffeeTypes.stream().map(cafe::getCoffeeByCoffeeType).toList();
    }
}
