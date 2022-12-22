package ca.ulaval.glo4002.cafe.small.cafe.domain.geolocalisation;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;
import ca.ulaval.glo4002.cafe.domain.geolocalisation.State;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StateTest {
    private static final String INVALID_STATE = "WWW";

    @Test
    public void givenValidState_whenCreatingFromString_shouldCreateInstance() {
        State createdState = State.fromString("AL");

        assertEquals(State.AL, createdState);
    }

    @Test
    public void givenInvalidState_whenCreatingFromString_shouldThrowInvalidConfigurationCountryException() {
        assertThrows(InvalidConfigurationCountryException.class,
            () -> State.fromString(INVALID_STATE));
    }
}
