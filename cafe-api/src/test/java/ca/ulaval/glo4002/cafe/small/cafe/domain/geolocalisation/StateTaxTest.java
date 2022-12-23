package ca.ulaval.glo4002.cafe.small.cafe.domain.geolocalisation;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;
import ca.ulaval.glo4002.cafe.domain.taxing.StateTax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StateTaxTest {
    private static final String INVALID_STATE = "WWW";

    @Test
    public void givenValidState_whenCreatingFromString_shouldCreateInstance() {
        StateTax createdStateTax = StateTax.fromString("AL");

        assertEquals(StateTax.AL, createdStateTax);
    }

    @Test
    public void givenInvalidState_whenCreatingFromString_shouldThrowInvalidConfigurationCountryException() {
        assertThrows(InvalidConfigurationCountryException.class,
            () -> StateTax.fromString(INVALID_STATE));
    }
}
