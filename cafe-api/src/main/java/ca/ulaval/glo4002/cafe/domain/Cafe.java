package ca.ulaval.glo4002.cafe.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.billing.BillingSystem;
import ca.ulaval.glo4002.cafe.domain.billing.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.bill.BillFactory;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerAlreadyVisitedException;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNoBillException;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.exception.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.domain.exception.NoReservationsFoundException;
import ca.ulaval.glo4002.cafe.domain.geolocalisation.Location;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.Quantity;
import ca.ulaval.glo4002.cafe.domain.layout.Layout;
import ca.ulaval.glo4002.cafe.domain.layout.LayoutFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.ordering.OrderingSystem;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeFactory;
import ca.ulaval.glo4002.cafe.domain.ordering.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Menu;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Order;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Recipe;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.ReservationStrategy;

public class Cafe {
    private final ReservationStrategyFactory reservationStrategyFactory;
    private final Layout layout;
    private final List<Reservation> reservations = new ArrayList<>();
    private final Inventory inventory;
    private final Menu menu;
    private final OrderingSystem orderingSystem;
    private final BillingSystem billingSystem;
    private TipRate groupTipRate;
    private CubeSize cubeSize;
    private CafeName cafeName;
    private Location location;
    private ReservationStrategy reservationStrategy;

    public Cafe(List<CubeName> cubeNames, CafeConfiguration cafeConfiguration) {
        reservationStrategyFactory = new ReservationStrategyFactory();

        LayoutFactory layoutFactory = new LayoutFactory();
        this.layout = layoutFactory.createLayout(cafeConfiguration.cubeSize(), cubeNames);

        this.inventory = new Inventory();
        CoffeeFactory coffeeFactory = new CoffeeFactory();
        this.menu = new Menu(new ArrayList<>(Arrays.asList(
            coffeeFactory.createCoffee(new CoffeeType("Americano"), new Amount(2.25f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Water, new Quantity(50))))),
            coffeeFactory.createCoffee(new CoffeeType("Dark Roast"), new Amount(2.10f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(40)),
                new Ingredient(IngredientType.Water, new Quantity(40)),
                new Ingredient(IngredientType.Chocolate, new Quantity(10)),
                new Ingredient(IngredientType.Milk, new Quantity(10))))),
            coffeeFactory.createCoffee(new CoffeeType("Cappuccino"), new Amount(3.29f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Water, new Quantity(40)),
                new Ingredient(IngredientType.Milk, new Quantity(10))))),
            coffeeFactory.createCoffee(new CoffeeType("Espresso"), new Amount(2.95f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(60))))),
            coffeeFactory.createCoffee(new CoffeeType("Flat White"), new Amount(3.75f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Milk, new Quantity(50))))),
            coffeeFactory.createCoffee(new CoffeeType("Latte"), new Amount(2.95f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Milk, new Quantity(50))))),
            coffeeFactory.createCoffee(new CoffeeType("Macchiato"), new Amount(4.75f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(80)),
                new Ingredient(IngredientType.Milk, new Quantity(20))))),
            coffeeFactory.createCoffee(new CoffeeType("Mocha"), new Amount(4.15f), new Recipe(List.of(
                new Ingredient(IngredientType.Espresso, new Quantity(50)),
                new Ingredient(IngredientType.Milk, new Quantity(40)),
                new Ingredient(IngredientType.Chocolate, new Quantity(10))))))));
        this.orderingSystem = new OrderingSystem();
        this.billingSystem = new BillingSystem(new BillFactory());

        updateConfiguration(cafeConfiguration);
    }

    public void updateConfiguration(CafeConfiguration cafeConfiguration) {
        this.cubeSize = cafeConfiguration.cubeSize();
        this.cafeName = cafeConfiguration.cafeName();
        this.reservationStrategy = reservationStrategyFactory.createReservationStrategy(cafeConfiguration.reservationType());
        this.groupTipRate = cafeConfiguration.groupTipRate();
        this.location = cafeConfiguration.location();
    }

    public CafeName getName() {
        return cafeName;
    }

    public Layout getLayout() {
        return layout;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void checkIn(Customer customer, Optional<GroupName> groupName) {
        checkIfCustomerAlreadyVisitedToday(customer.getId());
        assignSeatToCustomer(customer, groupName);
    }

    private void checkIfCustomerAlreadyVisitedToday(CustomerId customerId) {
        if (billingSystem.hasBillForCustomerId(customerId) || layout.isCustomerAlreadySeated(customerId)) {
            throw new CustomerAlreadyVisitedException();
        }
    }

    public Seat getSeatByCustomerId(CustomerId customerId) {
        return layout.getSeatByCustomerId(customerId);
    }

    public Order getOrderByCustomerId(CustomerId customerId) {
        layout.verifyIfCustomerIsAlreadySeated(customerId);
        return orderingSystem.getOrderByCustomerId(customerId);
    }

    public void makeReservation(Reservation reservation) {
        checkIfGroupNameAlreadyExists(reservation.name());
        reservationStrategy.makeReservation(reservation, layout.getCubes());
        reservations.add(reservation);
    }

    private void checkIfGroupNameAlreadyExists(GroupName name) {
        if (reservations.stream().map(Reservation::name).toList().contains(name)) {
            throw new DuplicateGroupNameException();
        }
    }

    public void close() {
        layout.reset(cubeSize);
        reservations.clear();
        orderingSystem.clear();
        billingSystem.clear();
        inventory.clear();
        menu.clear();
    }

    public void checkOut(CustomerId customerId) {
        Seat seat = this.layout.getSeatByCustomerId(customerId);
        boolean isCustomerInGroup = seat.isReservedForGroup();
        this.layout.checkout(customerId);
        billingSystem.createBill(customerId, orderingSystem, location, groupTipRate, isCustomerInGroup);
    }

    public void addCoffeeToMenu(Coffee coffee) {
        this.menu.addCoffee(coffee);
    }

    public void placeOrder(CustomerId customerId, Order order) {
        layout.verifyIfCustomerIsAlreadySeated(customerId);
        orderingSystem.placeOrder(customerId, order, inventory);
    }

    public void addIngredientsToInventory(List<Ingredient> ingredients) {
        inventory.add(ingredients);
    }

    public Bill getCustomerBill(CustomerId customerId) {
        if (!billingSystem.hasBillForCustomerId(customerId)) {
            if (layout.isCustomerAlreadySeated(customerId)) {
                throw new CustomerNoBillException();
            } else {
                throw new CustomerNotFoundException();
            }
        }
        return billingSystem.getBillByCustomerId(customerId);
    }

    private void assignSeatToCustomer(Customer customer, Optional<GroupName> groupName) {
        if (groupName.isPresent()) {
            validateHasReservation(groupName.get());
            layout.assignSeatToGroupMember(customer, groupName.get());
        } else {
            layout.assignSeatToIndividual(customer);
        }
    }

    private void validateHasReservation(GroupName groupName) {
        for (Reservation reservation : reservations) {
            if (reservation.name().equals(groupName)) {
                return;
            }
        }
        throw new NoReservationsFoundException();
    }

    public Coffee getCoffeeByCoffeeType(CoffeeType coffeeType) {
        return menu.getCoffeeByCoffeeType(coffeeType);
    }
}
