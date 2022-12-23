package ca.ulaval.glo4002.cafe.domain.geolocalisation;

import java.util.Map;
import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.geolocalisation.taxing.Tax;
import ca.ulaval.glo4002.cafe.domain.geolocalisation.taxing.strategy.TaxingCanada;
import ca.ulaval.glo4002.cafe.domain.geolocalisation.taxing.strategy.TaxingNone;
import ca.ulaval.glo4002.cafe.domain.geolocalisation.taxing.strategy.TaxingStrategy;
import ca.ulaval.glo4002.cafe.domain.geolocalisation.taxing.strategy.TaxingUnitedStates;

public record Location(Country country, Optional<Province> province, Optional<State> state) {
    private static final Map<Country, TaxingStrategy> TAXING_STRATEGIES =
        Map.of(Country.CA, new TaxingCanada(), Country.US, new TaxingUnitedStates(), Country.CL, new TaxingNone(), Country.None, new TaxingNone());

    public static Location fromDetails(String countryString, String provinceString, String stateString) {
        Country country = Country.fromString(countryString);
        Optional<Province> province = Optional.empty();
        Optional<State> state = Optional.empty();

        switch (country) {
            case CA -> province = Optional.of(Province.fromString(provinceString));
            case US -> state = Optional.of(State.fromString(stateString));
        }

        return new Location(country, province, state);
    }

    public Tax getTaxPercentage() {
        return TAXING_STRATEGIES.get(country()).getTaxRate(this);
    }
}
