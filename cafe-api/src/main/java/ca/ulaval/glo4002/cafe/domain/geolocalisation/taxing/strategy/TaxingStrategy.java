package ca.ulaval.glo4002.cafe.domain.geolocalisation.taxing.strategy;

import ca.ulaval.glo4002.cafe.domain.geolocalisation.Location;
import ca.ulaval.glo4002.cafe.domain.geolocalisation.taxing.Tax;

public interface TaxingStrategy {
    Tax getTaxRate(Location location);
}
