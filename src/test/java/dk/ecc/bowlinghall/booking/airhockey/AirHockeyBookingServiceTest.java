package dk.ecc.bowlinghall.booking.airhockey;

import dk.ecc.bowlinghall.booking.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class AirHockeyBookingServiceTest {

    @Autowired
    @Mock
    private AirHockeyBookingRepository airHockeyBookingRepository;

    @Mock
    private AirHockeyTableService airHockeyTableService;

    @InjectMocks
    private AirHockeyBookingService airHockeyBookingService;

    @BeforeEach
    void setUp(@Autowired AirHockeyBookingRepository airHockeyBookingRepository) {
        airHockeyBookingRepository.deleteAll();
        var booking1 = new AirHockeyBooking(
                "SHOW ME",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0),
                null
        );
        var booking2 = new AirHockeyBooking(
                "DONT SHOW ME",
                LocalDateTime.of(2025, 1, 8, 14, 0),
                LocalDateTime.of(2025, 1, 8, 16, 0),
                null
        );
        var booking3 = new AirHockeyBooking(
                "SHOW ME",
                LocalDateTime.of(2025, 1, 7, 12, 0),
                LocalDateTime.of(2025, 1, 7, 14, 0),
                null
        );

        airHockeyBookingRepository.save(booking1);
        airHockeyBookingRepository.save(booking2);
        airHockeyBookingRepository.save(booking3);
    }

    @AfterEach
    void tearDown(@Autowired AirHockeyBookingRepository airHockeyBookingRepository) {
        airHockeyBookingRepository.deleteAll();
    }

        @Test
    void updatePartialAirHockeyBookingUpdatesFieldsAndSavesBooking() {
        AirHockeyBooking booking = mock(AirHockeyBooking.class);
        when(airHockeyBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        AirHockeyBookingDTO dto = new AirHockeyBookingDTO(
                null, null, "test@example.com", null, null, null
        );

        airHockeyBookingService.updatePartialAirHockeyBooking(1L, dto);

        verify(airHockeyBookingRepository).save(any());
    }

    @Test
    void updatePartialAirHockeyBookingRemovesBookingWhenStatusIsCancelled() {
        AirHockeyBooking booking = mock(AirHockeyBooking.class);
        AirHockeyTable table = mock(AirHockeyTable.class);
        when(booking.getTable()).thenReturn(table);
        when(airHockeyBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        AirHockeyBookingDTO dto = new AirHockeyBookingDTO(
                null, null, null, null, null, Status.CANCELLED
        );

        airHockeyBookingService.updatePartialAirHockeyBooking(1L, dto);

        verify(table).removeBooking(booking);
    }

    @Test
    void updatePartialAirHockeyBookingThrowsExceptionWhenNoTablesAvailable() {
        AirHockeyBooking booking = mock(AirHockeyBooking.class);
        when(booking.getTable()).thenReturn(null);
        when(airHockeyBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(airHockeyTableService.findFirstAvailableAirHockeyTable(any(), any())).thenReturn(null);

        AirHockeyBookingDTO dto = new AirHockeyBookingDTO(
                null, null, null, LocalDateTime.parse("2024-07-01T10:00:00"), LocalDateTime.parse("2024-07-01T11:00:00"), null
        );

        assertThrows(IllegalArgumentException.class, () -> airHockeyBookingService.updatePartialAirHockeyBooking(1L, dto));
    }
    @Test
    void updatePartialAirHockeyBookingDoesNotThrowExceptionWhenStatusIsCancelled() {
        AirHockeyBooking booking = mock(AirHockeyBooking.class);
        when(booking.getTable()).thenReturn(null);
        when(airHockeyBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        AirHockeyBookingDTO dto = new AirHockeyBookingDTO(
                null, null, null, LocalDateTime.parse("2024-07-01T10:00:00"), LocalDateTime.parse("2024-07-01T11:00:00"), Status.CANCELLED
        );

        assertDoesNotThrow(() -> airHockeyBookingService.updatePartialAirHockeyBooking(1L, dto));
}
    @Test
    void updatePartialAirHockeyBookingReturnsNullWhenNoBookingFound() {
        when(airHockeyBookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        AirHockeyBookingDTO dto = new AirHockeyBookingDTO(
                null, null, "test@example.com", null, null, null
        );

        var result = airHockeyBookingService.updatePartialAirHockeyBooking(1L, dto);

        assertNull(result);
    }

    @Test
    void updatePartialAirHockeyBookingRemovesBookingWhenStatusIsNoShow() {
        AirHockeyBooking booking = mock(AirHockeyBooking.class);
        AirHockeyTable table = mock(AirHockeyTable.class);
        when(booking.getTable()).thenReturn(table);
        when(airHockeyBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        AirHockeyBookingDTO dto = new AirHockeyBookingDTO(
                null, null, null, null, null, Status.NO_SHOW
        );

        airHockeyBookingService.updatePartialAirHockeyBooking(1L, dto);

        verify(table).removeBooking(booking);
    }

    @Test
    void updatePartialAirHockeyBookingDoesNotSaveWhenNoFieldsUpdated() {
        AirHockeyBooking booking = mock(AirHockeyBooking.class);
        when(airHockeyBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        AirHockeyBookingDTO dto = new AirHockeyBookingDTO(
                null, null, null, null, null, null
        );

        airHockeyBookingService.updatePartialAirHockeyBooking(1L, dto);

        verify(airHockeyBookingRepository, never()).save(any());
    }

    @Test
    void getBowlingBookingsPagination() {
        var bookings = airHockeyBookingService.getAirHockeyBookings(LocalDateTime.of(2025, 1, 1, 12, 0), 7);

        assertEquals(2, bookings.size());
        assertFalse(bookings.stream().anyMatch(booking -> booking.customerEmail().equals("DONT SHOW ME")));
    }

}