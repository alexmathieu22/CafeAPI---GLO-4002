package ca.ulaval.glo4002.cafe.domain.taxing.strategy;

import ca.ulaval.glo4002.cafe.domain.taxing.LocationTax;
import ca.ulaval.glo4002.cafe.domain.taxing.Tax;

public interface TaxingStrategy {
    Tax getTaxRate(LocationTax locationTax);
}
