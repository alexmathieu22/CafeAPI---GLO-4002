package ca.ulaval.glo4002.cafe.domain.taxing;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;

public enum CountryTax {
    CA(new Tax(0.05f)),
    US(new Tax(0)),
    CL(new Tax(0.19f)),
    None(new Tax(0));

    private final Tax tax;

    CountryTax(Tax tax) {
        this.tax = tax;
    }

    public static CountryTax fromString(String country) {
        if (CountryTax.contains(country)) {
            return CountryTax.valueOf(country);
        }
        throw new InvalidConfigurationCountryException();
    }

    private static boolean contains(String other) {
        for (CountryTax countryTax : CountryTax.values()) {
            if (countryTax.name().equals(other)) {
                return true;
            }
        }
        return false;
    }

    public Tax getTax() {
        return tax;
    }
}
