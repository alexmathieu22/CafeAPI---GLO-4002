package ca.ulaval.glo4002.cafe.domain.geolocalisation.taxing.strategy;

import ca.ulaval.glo4002.cafe.domain.geolocalisation.taxing.Tax;
import ca.ulaval.glo4002.cafe.domain.geolocalisation.Location;

public class TaxingCanada implements TaxingStrategy {
    @Override
    public Tax getTaxRate(Location location) {
        return location.country().getTax().add(location.province().get().getTax());
    }
}
