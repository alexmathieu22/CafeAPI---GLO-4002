package ca.ulaval.glo4002.cafe.small.cafe.domain.valueobjects;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidGroupTipRateException;
import ca.ulaval.glo4002.cafe.domain.valueobjects.TipRate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TipRateTest {
    @Test
    public void whenCreatingTipRateWithValueUnder0_shouldThrowInvalidGroupTipRateException() {
        assertThrows(InvalidGroupTipRateException.class, () -> new TipRate(-2));
    }

    @Test
    public void whenCreatingTipRateWithValueOver100_shouldThrowInvalidGroupTipRateException() {
        assertThrows(InvalidGroupTipRateException.class, () -> new TipRate(120));
    }
}
