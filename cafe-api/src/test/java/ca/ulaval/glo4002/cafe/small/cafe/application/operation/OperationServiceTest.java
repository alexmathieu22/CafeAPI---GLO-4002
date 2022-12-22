package ca.ulaval.glo4002.cafe.small.cafe.application.operation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.application.operation.OperationService;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeRepository;

import static org.mockito.Mockito.*;

public class OperationServiceTest {
    private OperationService operationService;
    private CafeRepository cafeRepository;

    @BeforeEach
    public void createCafeService() {
        cafeRepository = mock(CafeRepository.class);
        operationService = new OperationService(cafeRepository);
    }

    @Test
    public void whenClosingCafe_shouldGetCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        operationService.closeCafe();

        verify(cafeRepository).get();
    }

    @Test
    public void whenClosingCafe_shouldCloseCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        operationService.closeCafe();

        verify(mockCafe).close();
    }

    @Test
    public void whenClosingCafe_shouldUpdateCafe() {
        Cafe mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);

        operationService.closeCafe();

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }
}
