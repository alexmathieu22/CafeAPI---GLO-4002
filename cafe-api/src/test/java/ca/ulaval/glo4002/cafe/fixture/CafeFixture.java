package ca.ulaval.glo4002.cafe.fixture;

import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.billing.TipRate;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.taxing.CountryTax;
import ca.ulaval.glo4002.cafe.domain.taxing.Location;
import ca.ulaval.glo4002.cafe.domain.taxing.ProvinceTax;
import ca.ulaval.glo4002.cafe.domain.valueobjects.CafeConfiguration;
import ca.ulaval.glo4002.cafe.domain.valueobjects.CafeName;

public class CafeFixture {
    private static final ReservationType RESERVATION_STRATEGY_TYPE = ReservationType.Default;
    private List<CubeName> cubeNames =
        List.of(new CubeName("Wanda"), new CubeName("Tinker Bell"), new CubeName("Bloom"), new CubeName("Merryweather"));
    private CafeName name = new CafeName("Les 4-Fées");
    private CubeSize cubeSize = new CubeSize(4);
    private TipRate groupTipRate = new TipRate(0.05f);
    private Location location = new Location(CountryTax.CA, Optional.of(ProvinceTax.AB), Optional.empty());

    public CafeFixture withName(CafeName name) {
        this.name = name;
        return this;
    }

    public CafeFixture withCubeNames(List<CubeName> cubeNames) {
        this.cubeNames = cubeNames;
        return this;
    }

    public CafeFixture withCubeSize(CubeSize cubeSize) {
        this.cubeSize = cubeSize;
        return this;
    }

    public CafeFixture withLocation(Location location) {
        this.location = location;
        return this;
    }

    public CafeFixture withGroupTipRate(TipRate groupTipRate) {
        this.groupTipRate = groupTipRate;
        return this;
    }

    public Cafe build() {
        CafeConfiguration cafeConfiguration = new CafeConfiguration(cubeSize, name, RESERVATION_STRATEGY_TYPE, location, groupTipRate);
        return new Cafe(cubeNames, cafeConfiguration);
    }
}
