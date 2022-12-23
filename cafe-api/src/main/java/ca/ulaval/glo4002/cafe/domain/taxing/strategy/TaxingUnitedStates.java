package ca.ulaval.glo4002.cafe.domain.taxing.strategy;

import ca.ulaval.glo4002.cafe.domain.taxing.LocationTax;
import ca.ulaval.glo4002.cafe.domain.taxing.Tax;

public class TaxingUnitedStates implements TaxingStrategy {
    @Override
    public Tax getTaxRate(LocationTax locationTax) {
        return locationTax.state().get().getTax();
    }
}
