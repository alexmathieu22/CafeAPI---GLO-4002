package ca.ulaval.glo4002.cafe.domain.taxing;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;

public enum StateTax {
    AL(new Tax(0.04f)),
    AZ(new Tax(0.056f)),
    CA(new Tax(0.0725f)),
    FL(new Tax(0.06f)),
    ME(new Tax(0.055f)),
    NY(new Tax(0.04f)),
    TX(new Tax(0.0625f));

    private final Tax tax;

    StateTax(Tax tax) {
        this.tax = tax;
    }

    public static StateTax fromString(String state) {
        if (StateTax.contains(state)) {
            return StateTax.valueOf(state);
        }
        throw new InvalidConfigurationCountryException();
    }

    private static boolean contains(String other) {
        for (StateTax stateTax : StateTax.values()) {
            if (stateTax.name().equals(other)) {
                return true;
            }
        }
        return false;
    }

    public Tax getTax() {
        return tax;
    }
}
