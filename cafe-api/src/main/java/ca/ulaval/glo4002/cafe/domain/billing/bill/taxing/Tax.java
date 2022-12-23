package ca.ulaval.glo4002.cafe.domain.billing.bill.taxing;

public record Tax(float value) {
    public Tax add(Tax tax) {
        return new Tax(value + tax.value());
    }
}
