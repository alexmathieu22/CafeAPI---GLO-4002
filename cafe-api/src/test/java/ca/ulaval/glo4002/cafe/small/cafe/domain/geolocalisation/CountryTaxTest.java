package ca.ulaval.glo4002.cafe.small.cafe.domain.geolocalisation;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;
import ca.ulaval.glo4002.cafe.domain.taxing.CountryTax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CountryTaxTest {
    private static final String INVALID_COUNTRY = "WWW";

    @Test
    public void givenValidCountry_whenCreatingFromString_shouldCreateInstance() {
        CountryTax createdCountryTax = CountryTax.fromString("CA");

        assertEquals(CountryTax.CA, createdCountryTax);
    }

    @Test
    public void givenInvalidCountry_whenCreatingFromString_shouldThrowInvalidConfigurationCountryException() {
        assertThrows(InvalidConfigurationCountryException.class,
            () -> CountryTax.fromString(INVALID_COUNTRY));
    }
}
