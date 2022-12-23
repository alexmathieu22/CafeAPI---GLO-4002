package ca.ulaval.glo4002.cafe.fixture;

import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.billing.TipRate;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.taxing.CountryTax;
import ca.ulaval.glo4002.cafe.domain.taxing.LocationTax;
import ca.ulaval.glo4002.cafe.domain.taxing.ProvinceTax;
import ca.ulaval.glo4002.cafe.domain.taxing.StateTax;
import ca.ulaval.glo4002.cafe.domain.valueobjects.CafeConfiguration;
import ca.ulaval.glo4002.cafe.domain.valueobjects.CafeName;

public class CafeConfigurationFixture {
    private CubeSize cubeSize = new CubeSize(4);
    private CafeName name = new CafeName("Les 4-Fées");
    private ReservationType reservationType = ReservationType.Default;
    private CountryTax countryTax = CountryTax.CA;
    private Optional<ProvinceTax> province = Optional.of(ProvinceTax.QC);
    private Optional<StateTax> state = Optional.empty();
    private TipRate groupTipRate = new TipRate(0.05f);

    public CafeConfigurationFixture withCubeSize(CubeSize cubeSize) {
        this.cubeSize = cubeSize;
        return this;
    }

    public CafeConfigurationFixture withName(CafeName name) {
        this.name = name;
        return this;
    }

    public CafeConfigurationFixture withReservationType(ReservationType reservationType) {
        this.reservationType = reservationType;
        return this;
    }

    public CafeConfigurationFixture withCountry(CountryTax countryTax) {
        this.countryTax = countryTax;
        return this;
    }

    public CafeConfigurationFixture withProvince(ProvinceTax provinceTax) {
        this.province = Optional.of(provinceTax);
        return this;
    }

    public CafeConfigurationFixture withState(StateTax stateTax) {
        this.state = Optional.of(stateTax);
        return this;
    }

    public CafeConfigurationFixture withGroupTipRate(TipRate groupTipRate) {
        this.groupTipRate = groupTipRate;
        return this;
    }

    public CafeConfiguration build() {
        return new CafeConfiguration(cubeSize, name, reservationType, new LocationTax(countryTax, province, state), groupTipRate);
    }
}
