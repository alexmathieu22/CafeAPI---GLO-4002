package ca.ulaval.glo4002.cafe.domain;

import java.util.ArrayList;
import java.util.HashMap;
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
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.layout.Layout;
import ca.ulaval.glo4002.cafe.domain.layout.LayoutFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.ordering.OrderingSystem;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Order;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.ReservationStrategy;

public class Cafe {
    private final ReservationStrategyFactory reservationStrategyFactory;
    private final Layout layout;
    private final List<Reservation> reservations = new ArrayList<>();
    private final HashMap<CustomerId, Bill> bills = new HashMap<>();
    private final Inventory inventory;
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

    public void close() {
        layout.reset(cubeSize);
        reservations.clear();
        orderingSystem.clear();
        billingSystem.clear();
        inventory.clear();
    }

    public void checkOut(CustomerId customerId) {
        Seat seat = this.layout.getSeatByCustomerId(customerId);
        boolean isCustomerInGroup = seat.isReservedForGroup();
        this.layout.checkout(customerId);
        billingSystem.createBill(customerId, orderingSystem, location, groupTipRate, isCustomerInGroup);
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

    private void checkIfCustomerAlreadyVisitedToday(CustomerId customerId) {
        if (billingSystem.hasBillForCustomerId(customerId) || layout.isCustomerAlreadySeated(customerId)) {
            throw new CustomerAlreadyVisitedException();
        }
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

    private void checkIfGroupNameAlreadyExists(GroupName name) {
        if (reservations.stream().map(Reservation::name).toList().contains(name)) {
            throw new DuplicateGroupNameException();
        }
    }
}
