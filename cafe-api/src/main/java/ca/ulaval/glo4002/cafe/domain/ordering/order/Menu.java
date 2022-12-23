package ca.ulaval.glo4002.cafe.domain.ordering.order;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.cafe.domain.exception.DuplicateCoffeeNameException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidMenuOrderException;

public class Menu {
    private final Set<CoffeeType> defaultCoffees;
    private final List<Coffee> coffees;

    public Menu(List<Coffee> coffees) {
        this.coffees = coffees;
        this.defaultCoffees = coffees.stream().map(Coffee::coffeeType).collect(Collectors.toSet());
    }

    private boolean contains(CoffeeType coffeeType) {
        return coffees.stream().anyMatch(coffee -> coffee.coffeeType().equals(coffeeType));
    }

    public void addCoffee(Coffee coffee) {
        if (contains(coffee.coffeeType())) {
            throw new DuplicateCoffeeNameException();
        }
        coffees.add(coffee);
    }

    public Coffee getCoffeeByCoffeeType(CoffeeType coffeeType) {
        return coffees.stream().filter(coffee -> coffee.coffeeType().equals(coffeeType)).findFirst().orElseThrow(InvalidMenuOrderException::new);
    }

    public void clear() {
        coffees.removeIf(coffee -> !defaultCoffees.contains(coffee.coffeeType()));
    }
}
