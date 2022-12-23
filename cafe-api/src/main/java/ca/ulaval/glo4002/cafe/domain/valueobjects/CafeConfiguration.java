package ca.ulaval.glo4002.cafe.domain.valueobjects;

import ca.ulaval.glo4002.cafe.domain.billing.TipRate;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.taxing.LocationTax;

public record CafeConfiguration(CubeSize cubeSize, CafeName cafeName, ReservationType reservationType,
                                LocationTax locationTax, TipRate groupTipRate) {
}
