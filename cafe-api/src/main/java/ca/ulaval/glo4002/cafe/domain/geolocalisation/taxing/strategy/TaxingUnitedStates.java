package ca.ulaval.glo4002.cafe.domain.geolocalisation.taxing.strategy;

import ca.ulaval.glo4002.cafe.domain.geolocalisation.Location;
import ca.ulaval.glo4002.cafe.domain.geolocalisation.taxing.Tax;

public class TaxingUnitedStates implements TaxingStrategy {
    @Override
    public Tax getTaxRate(Location location) {
        return location.country().getTax().add(location.state().get().getTax());
    }
}
