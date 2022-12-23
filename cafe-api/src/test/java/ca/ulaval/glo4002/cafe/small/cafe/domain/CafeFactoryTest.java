package ca.ulaval.glo4002.cafe.small.cafe.domain;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.billing.TipRate;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.taxing.CountryTax;
import ca.ulaval.glo4002.cafe.domain.taxing.LocationTax;
import ca.ulaval.glo4002.cafe.domain.valueobjects.CafeName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CafeFactoryTest {
    private static final CafeName CAFE_NAME = new CafeName("Les 4-Fées");
    private static final CubeSize CUBE_SIZE = new CubeSize(4);
    private static final List<CubeName> CUBE_NAMES =
        List.of(new CubeName("Wanda"), new CubeName("Tinker Bell"), new CubeName("Bloom"), new CubeName("Merryweather"));
    private static final ReservationType RESERVATION_STRATEGY_TYPE = ReservationType.Default;
    private static final TipRate GROUP_TIP_RATE = new TipRate(0);
    private static final LocationTax LOCATION_TAX = new LocationTax(CountryTax.None, Optional.empty(), Optional.empty());

    private CafeFactory cafeFactory;

    @BeforeEach
    public void createCafeFactory() {
        cafeFactory = new CafeFactory();
    }

    @Test
    public void whenCreatingCafe_shouldHaveDefaultName() {
        Cafe cafe = cafeFactory.createCafe(CUBE_SIZE, CAFE_NAME, RESERVATION_STRATEGY_TYPE, LOCATION_TAX, GROUP_TIP_RATE, CUBE_NAMES);

        assertEquals(CAFE_NAME, cafe.getName());
    }

    @Test
    public void whenCreatingCafe_shouldCreateCubesListWithSortedSpecificCubesNames() {
        List<CubeName> expectedCubeNames = List.of(new CubeName("Bloom"), new CubeName("Merryweather"), new CubeName("Tinker Bell"), new CubeName("Wanda"));

        Cafe cafe = cafeFactory.createCafe(CUBE_SIZE, CAFE_NAME, RESERVATION_STRATEGY_TYPE, LOCATION_TAX, GROUP_TIP_RATE, CUBE_NAMES);

        assertEquals(expectedCubeNames, cafe.getLayout().getCubes().stream().map(Cube::getName).toList());
    }
}
