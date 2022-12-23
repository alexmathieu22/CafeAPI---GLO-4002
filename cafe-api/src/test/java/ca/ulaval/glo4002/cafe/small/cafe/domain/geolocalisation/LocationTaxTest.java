package ca.ulaval.glo4002.cafe.small.cafe.domain.geolocalisation;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;
import ca.ulaval.glo4002.cafe.domain.taxing.CountryTax;
import ca.ulaval.glo4002.cafe.domain.taxing.LocationTax;
import ca.ulaval.glo4002.cafe.domain.taxing.ProvinceTax;
import ca.ulaval.glo4002.cafe.domain.taxing.StateTax;
import ca.ulaval.glo4002.cafe.domain.taxing.Tax;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTaxTest {
    private static final String INVALID_COUNTRY = "WWW";
    private static final String INVALID_PROVINCE = "BOB";
    private static final String INVALID_STATE = "EOE";
    private static final String A_VALID_PROVINCE = "QC";
    private static final String A_VALID_STATE = "AL";

    @Test
    public void givenLocationWithCountryAndProvince_whenGettingTaxPercentage_shouldReturnRightTaxPercentage() {
        LocationTax locationTax = new LocationTax(CountryTax.CA, Optional.of(ProvinceTax.QC), Optional.empty());
        Tax expectedTax = CountryTax.CA.getTax().add(ProvinceTax.QC.getTax());

        Tax taxReceived = locationTax.getTaxPercentage();

        assertEquals(expectedTax, taxReceived);
    }

    @Test
    public void givenLocationWithCountryAndState_whenGettingTaxPercentage_shouldReturnRightTaxPercentage() {
        LocationTax locationTax = new LocationTax(CountryTax.US, Optional.empty(), Optional.of(StateTax.CA));
        Tax expectedTax = CountryTax.US.getTax().add(StateTax.CA.getTax());

        Tax taxReceived = locationTax.getTaxPercentage();

        assertEquals(expectedTax, taxReceived);
    }

    @Test
    public void givenLocationWithOnlyCountry_whenGettingTaxPercentage_shouldReturnRightTaxPercentage() {
        LocationTax locationTax = new LocationTax(CountryTax.CL, Optional.empty(), Optional.empty());
        Tax expectedTax = CountryTax.CL.getTax();

        Tax taxReceived = locationTax.getTaxPercentage();

        assertEquals(expectedTax, taxReceived);
    }

    @Test
    public void givenLocationWithNoneCountry_whenGettingTaxPercentage_shouldReturnZero() {
        LocationTax locationTax = new LocationTax(CountryTax.None, Optional.empty(), Optional.empty());
        Tax expectedTax = CountryTax.None.getTax();

        Tax taxReceived = locationTax.getTaxPercentage();

        assertEquals(expectedTax, taxReceived);
    }

    @Test
    public void givenAnInvalidCountry_whenCreatingLocationFromDetails_shouldThrowInvalidConfigurationCountryException() {
        assertThrows(InvalidConfigurationCountryException.class, () -> LocationTax.fromDetails(INVALID_COUNTRY, A_VALID_PROVINCE, A_VALID_STATE));
    }

    @Test
    public void givenCACountryAndInvalidProvince_whenCreatingLocationFromDetails_shouldThrowInvalidConfigurationCountryException() {
        assertThrows(InvalidConfigurationCountryException.class, () -> LocationTax.fromDetails("CA", INVALID_PROVINCE, A_VALID_STATE));
    }

    @Test
    public void givenUSCountryAndInvalidState_whenCreatingLocationFromDetails_shouldThrowInvalidConfigurationCountryException() {
        assertThrows(InvalidConfigurationCountryException.class, () -> LocationTax.fromDetails("US", A_VALID_PROVINCE, INVALID_STATE));
    }

    @Test
    public void givenCACountryAndInvalidState_whenCreatingLocationFromDetails_shouldNotThrowInvalidConfigurationRequestException() {
        assertDoesNotThrow(() -> LocationTax.fromDetails("CA", A_VALID_PROVINCE, INVALID_STATE));
    }

    @Test
    public void givenUSCountryAndInvalidProvince_whenCreatingLocationFromDetails_shouldNotThrowInvalidConfigurationRequestException() {
        assertDoesNotThrow(() -> LocationTax.fromDetails("US", INVALID_PROVINCE, A_VALID_STATE));
    }

    @Test
    public void givenCountryWithProvinceOnly_whenCreatingLocationFromDetails_shouldIgnoreState() {
        LocationTax locationTax = LocationTax.fromDetails("CA", A_VALID_PROVINCE, A_VALID_STATE);

        assertTrue(locationTax.state().isEmpty());
    }

    @Test
    public void givenCountryWithStateOnly_whenCreatingLocationFromDetails_shouldIgnoreProvince() {
        LocationTax locationTax = LocationTax.fromDetails("US", A_VALID_PROVINCE, A_VALID_STATE);

        assertTrue(locationTax.province().isEmpty());
    }

    @Test
    public void givenCountryWithNoProvinceAndState_whenCreatingLocationFromDetails_shouldIgnoreProvinceAndState() {
        LocationTax locationTax = LocationTax.fromDetails("None", A_VALID_PROVINCE, A_VALID_STATE);

        assertTrue(locationTax.province().isEmpty());
        assertTrue(locationTax.state().isEmpty());
    }
}
