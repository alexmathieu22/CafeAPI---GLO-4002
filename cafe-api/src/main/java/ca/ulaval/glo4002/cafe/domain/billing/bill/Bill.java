package ca.ulaval.glo4002.cafe.domain.billing.bill;

import ca.ulaval.glo4002.cafe.domain.ordering.order.Order;
import ca.ulaval.glo4002.cafe.domain.valueobjects.Amount;

public record Bill(Order order, Amount subtotal, Amount taxes, Amount tip, Amount total) {
    public Bill(Order order, Amount subtotal, Amount taxes, Amount tip) {
        this(order, subtotal, taxes, tip, subtotal.add(taxes).add(tip));
    }
}
