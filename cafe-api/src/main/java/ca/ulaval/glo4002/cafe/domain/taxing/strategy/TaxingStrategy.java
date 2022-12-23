package ca.ulaval.glo4002.cafe.domain.taxing.strategy;

import ca.ulaval.glo4002.cafe.domain.taxing.Location;
import ca.ulaval.glo4002.cafe.domain.taxing.Tax;

public interface TaxingStrategy {
    Tax getTaxRate(Location location);
}
