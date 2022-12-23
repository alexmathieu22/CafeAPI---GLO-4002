package ca.ulaval.glo4002.cafe.domain.taxing;

import java.util.Map;
import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.taxing.strategy.TaxingCanada;
import ca.ulaval.glo4002.cafe.domain.taxing.strategy.TaxingNone;
import ca.ulaval.glo4002.cafe.domain.taxing.strategy.TaxingStrategy;
import ca.ulaval.glo4002.cafe.domain.taxing.strategy.TaxingUnitedStates;

public record Location(CountryTax countryTax, Optional<ProvinceTax> province, Optional<StateTax> state) {
    private static final Map<CountryTax, TaxingStrategy> TAXING_STRATEGIES =
        Map.of(CountryTax.CA, new TaxingCanada(), CountryTax.US, new TaxingUnitedStates(), CountryTax.CL, new TaxingNone(), CountryTax.None, new TaxingNone());

    public static Location fromDetails(String countryString, String provinceString, String stateString) {
        CountryTax countryTax = CountryTax.fromString(countryString);
        Optional<ProvinceTax> province = Optional.empty();
        Optional<StateTax> state = Optional.empty();

        switch (countryTax) {
            case CA -> province = Optional.of(ProvinceTax.fromString(provinceString));
            case US -> state = Optional.of(StateTax.fromString(stateString));
        }

        return new Location(countryTax, province, state);
    }

    public Tax getTaxPercentage() {
        return TAXING_STRATEGIES.get(countryTax()).getTaxRate(this);
    }
}
