package ca.ulaval.glo4002.cafe.small.cafe.domain.geolocalisation;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;
import ca.ulaval.glo4002.cafe.domain.taxing.ProvinceTax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProvinceTaxTest {
    private static final String INVALID_PROVINCE = "WWW";

    @Test
    public void givenValidProvince_whenCreatingFromString_shouldCreateInstance() {
        ProvinceTax createdProvinceTax = ProvinceTax.fromString("QC");

        assertEquals(ProvinceTax.QC, createdProvinceTax);
    }

    @Test
    public void givenInvalidProvince_whenCreatingFromString_shouldThrowInvalidConfigurationCountryException() {
        assertThrows(InvalidConfigurationCountryException.class, () -> ProvinceTax.fromString(INVALID_PROVINCE));
    }
}
