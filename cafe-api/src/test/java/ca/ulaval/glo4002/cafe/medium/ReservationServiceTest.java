package ca.ulaval.glo4002.cafe.medium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.application.reservation.ReservationService;
import ca.ulaval.glo4002.cafe.application.reservation.dto.ReservationDTO;
import ca.ulaval.glo4002.cafe.application.reservation.parameter.ReservationRequestParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupSize;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.infrastructure.InMemoryCafeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationServiceTest {
    private static final GroupSize A_GROUP_SIZE = new GroupSize(4);
    private static final GroupName A_GROUP_NAME = new GroupName("My group");
    private static final ReservationRequestParams A_RESERVATION_REQUEST_PARAMS = new ReservationRequestParams(A_GROUP_NAME.value(), A_GROUP_SIZE.value());

    private ReservationService reservationService;

    private void initializeCafe(CafeFactory cafeFactory, CafeRepository cafeRepository) {
        Cafe cafe = cafeFactory.createCafe();
        cafeRepository.saveOrUpdate(cafe);
    }

    @BeforeEach
    public void instanciateAttributes() {
        CafeFactory cafeFactory = new CafeFactory();
        CafeRepository cafeRepository = new InMemoryCafeRepository();
        reservationService = new ReservationService(cafeRepository, new ReservationFactory());
        initializeCafe(cafeFactory, cafeRepository);
    }

    @Test
    public void whenMakingReservation_shouldSaveReservation() {
        reservationService.makeReservation(A_RESERVATION_REQUEST_PARAMS);
        ReservationDTO actualReservationDTO = reservationService.getReservations();

        assertEquals(A_GROUP_SIZE, actualReservationDTO.reservations().get(0).size());
        assertEquals(A_GROUP_NAME, actualReservationDTO.reservations().get(0).name());
    }
}
