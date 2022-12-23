package ca.ulaval.glo4002.cafe.application.parameter;

import ca.ulaval.glo4002.cafe.domain.billing.TipRate;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.taxing.LocationTax;
import ca.ulaval.glo4002.cafe.domain.valueobjects.CafeName;

public record ConfigurationParams(CubeSize cubeSize, CafeName cafeName, ReservationType reservationType, LocationTax locationTax, TipRate groupTipRate) {
    public ConfigurationParams(int cubeSize, String cafeName, String reservationType, String country, String province, String state, float groupTipRate) {
        this(new CubeSize(cubeSize), new CafeName(cafeName), ReservationType.fromString(reservationType), LocationTax.fromDetails(country, province, state),
            new TipRate(groupTipRate / 100));
    }

    public static ConfigurationParams from(int cubeSize, String cafeName, String reservationType, String country, String province, String state,
                                           float groupTipRate) {
        return new ConfigurationParams(cubeSize, cafeName, reservationType, country, province, state, groupTipRate);
    }
}
