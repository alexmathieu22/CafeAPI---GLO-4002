package ca.ulaval.glo4002.cafe.domain.valueobjects;

import ca.ulaval.glo4002.cafe.domain.geolocalisation.Location;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;

public record CafeConfiguration(CubeSize cubeSize, CafeName cafeName, ReservationType reservationType,
                                Location location, TipRate groupTipRate) {
}
