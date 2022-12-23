package ca.ulaval.glo4002.cafe.domain;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.billing.TipRate;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.taxing.Location;
import ca.ulaval.glo4002.cafe.domain.valueobjects.CafeConfiguration;
import ca.ulaval.glo4002.cafe.domain.valueobjects.CafeName;

public class CafeFactory {
    public Cafe createCafe(CubeSize cubeSize, CafeName cafeName, ReservationType reservationType, Location location, TipRate groupTipRate,
                           List<CubeName> cubeNames) {
        CafeConfiguration cafeConfiguration = new CafeConfiguration(cubeSize, cafeName, reservationType, location, groupTipRate);
        return new Cafe(cubeNames, cafeConfiguration);
    }
}
