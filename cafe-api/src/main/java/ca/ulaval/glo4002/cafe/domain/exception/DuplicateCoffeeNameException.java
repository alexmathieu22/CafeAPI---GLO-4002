package ca.ulaval.glo4002.cafe.domain.exception;

public class DuplicateCoffeeNameException extends CafeException {
    public DuplicateCoffeeNameException() {
        super("DUPLICATE_COFFEE_NAME", "This coffee name is already used.");
    }
}
