package ca.ulaval.glo4002.cafe.domain.taxing;

import java.util.Map;
import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.taxing.strategy.TaxingCanada;
import ca.ulaval.glo4002.cafe.domain.taxing.strategy.TaxingChile;
import ca.ulaval.glo4002.cafe.domain.taxing.strategy.TaxingNone;
import ca.ulaval.glo4002.cafe.domain.taxing.strategy.TaxingStrategy;
import ca.ulaval.glo4002.cafe.domain.taxing.strategy.TaxingUnitedStates;

public record LocationTax(CountryTax countryTax, Optional<ProvinceTax> province, Optional<StateTax> state) {
    private static final Map<CountryTax, TaxingStrategy> TAXING_STRATEGIES =
        Map.of(CountryTax.CA, new TaxingCanada(),
            CountryTax.US, new TaxingUnitedStates(),
            CountryTax.CL, new TaxingChile(),
            CountryTax.None, new TaxingNone()
        );

    public static LocationTax fromDetails(String countryString, String provinceString, String stateString) {
        CountryTax countryTax = CountryTax.fromString(countryString);
        Optional<ProvinceTax> province = Optional.empty();
        Optional<StateTax> state = Optional.empty();

        switch (countryTax) {
            case CA -> province = Optional.of(ProvinceTax.fromString(provinceString));
            case US -> state = Optional.of(StateTax.fromString(stateString));
        }

        return new LocationTax(countryTax, province, state);
    }

    public Tax getTaxPercentage() {
        return TAXING_STRATEGIES.get(countryTax()).getTaxRate(this);
    }
}
