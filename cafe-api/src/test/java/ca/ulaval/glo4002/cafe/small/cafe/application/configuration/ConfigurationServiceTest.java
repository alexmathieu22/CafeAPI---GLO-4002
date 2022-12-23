package ca.ulaval.glo4002.cafe.small.cafe.application.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ca.ulaval.glo4002.cafe.application.configuration.ConfigurationService;
import ca.ulaval.glo4002.cafe.application.parameter.ConfigurationParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.valueobjects.CafeConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ConfigurationServiceTest {
    private static final ConfigurationParams A_CONFIGURATION_PARAMS = new ConfigurationParams(4, "Les 4-Fées", "Default", "CA", "QC", "", 5);

    private ConfigurationService configurationService;
    private CafeRepository cafeRepository;

    @BeforeEach
    public void createConfigurationService() {
        cafeRepository = mock(CafeRepository.class);
        configurationService = new ConfigurationService(cafeRepository);
    }

    @Test
    public void whenUpdatingConfiguration_shouldGetCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        configurationService.updateConfiguration(A_CONFIGURATION_PARAMS);

        verify(cafeRepository).get();
    }

    @Test
    public void whenUpdatingConfiguration_shouldCloseCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        configurationService.updateConfiguration(A_CONFIGURATION_PARAMS);

        verify(mockCafe).close();
    }

    @Test
    public void whenUpdatingConfiguration_shouldUpdateCafeConfiguration() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);
        ArgumentCaptor<CafeConfiguration> argument = ArgumentCaptor.forClass(CafeConfiguration.class);
        CafeConfiguration expectedConfiguration =
            new CafeConfiguration(A_CONFIGURATION_PARAMS.cubeSize(), A_CONFIGURATION_PARAMS.cafeName(), A_CONFIGURATION_PARAMS.reservationType(),
                A_CONFIGURATION_PARAMS.location(), A_CONFIGURATION_PARAMS.groupTipRate());

        configurationService.updateConfiguration(A_CONFIGURATION_PARAMS);

        verify(mockCafe).updateConfiguration(argument.capture());
        assertEquals(expectedConfiguration, argument.getValue());
    }

    @Test
    public void whenUpdatingConfiguration_shouldUpdateCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        configurationService.updateConfiguration(A_CONFIGURATION_PARAMS);

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }
}
