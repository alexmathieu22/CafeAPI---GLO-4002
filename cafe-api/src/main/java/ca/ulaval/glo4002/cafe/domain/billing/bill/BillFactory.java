package ca.ulaval.glo4002.cafe.domain.billing.bill;

import ca.ulaval.glo4002.cafe.domain.billing.bill.taxing.Tax;
import ca.ulaval.glo4002.cafe.domain.ordering.order.Order;
import ca.ulaval.glo4002.cafe.domain.valueobjects.Amount;
import ca.ulaval.glo4002.cafe.domain.valueobjects.TipRate;

public class BillFactory {
    public Bill createBill(Order order, Tax taxRate, TipRate groupTipRate, boolean isInGroup) {
        Amount subtotal = getOrderSubtotal(order);
        Amount taxes = new Amount(subtotal.value() * taxRate.value());
        Amount tip = isInGroup ? new Amount(subtotal.value() * groupTipRate.value()) : new Amount(0);
        return new Bill(new Order(order.items()), subtotal, taxes, tip);
    }

    private Amount getOrderSubtotal(Order order) {
        return new Amount(order.items().stream()
            .map(coffee -> coffee.price().value())
            .reduce(0f, Float::sum));
    }
}
