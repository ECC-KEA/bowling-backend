package dk.ecc.bowlinghall.booking.bowling;

import dk.ecc.bowlinghall.booking.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class BowlingBookingServiceTest {

    @Autowired
    @Mock
    private BowlingBookingRepository bowlingBookingRepository;

    @Mock
    private BowlingLaneService bowlingLaneService;

    @InjectMocks
    private BowlingBookingService bowlingBookingService;

    @BeforeEach
    void setUp(@Autowired BowlingBookingRepository bowlingBookingRepository) {
        var booking1 = new BowlingBooking(
                "SHOW ME",
                LocalDateTime.of(2025, 1, 1, 12, 0),
                LocalDateTime.of(2025, 1, 1, 14, 0),
                null
        );
        var booking2 = new BowlingBooking(
                "DONT SHOW ME",
                LocalDateTime.of(2025, 1, 8, 14, 0),
                LocalDateTime.of(2025, 1, 8, 16, 0),
                null
        );
        var booking3 = new BowlingBooking(
                "SHOW ME",
                LocalDateTime.of(2025, 1, 7, 12, 0),
                LocalDateTime.of(2025, 1, 7, 14, 0),
                null
        );

        bowlingBookingRepository.save(booking1);
        bowlingBookingRepository.save(booking2);
        bowlingBookingRepository.save(booking3);
    }

    @AfterEach
    void tearDown(@Autowired BowlingBookingRepository bowlingBookingRepository) {
        bowlingBookingRepository.deleteAll();
    }

    @Test
    void getBowlingBookingsPagination() {
        var bookings = bowlingBookingService.getBowlingBookings(LocalDateTime.of(2025, 1, 1, 12, 0), 7);

        assertEquals(2, bookings.size());
        assertFalse(bookings.stream().anyMatch(booking -> booking.customerEmail().equals("DONT SHOW ME")));
    }
    @Test
    void updatePartialBowlingBookingUpdatesFieldsAndSavesBooking() {
        BowlingBooking booking = mock(BowlingBooking.class);
        when(bowlingBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        BowlingBookingDTO dto = new BowlingBookingDTO(
                null, null, "newEmail@test.com", null, null, null, null
        );

        bowlingBookingService.updatePartialBowlingBooking(1L, dto);

        verify(bowlingBookingRepository).save(any());
    }

    @Test
    void updatePartialBowlingBookingReturnsNullWhenNoBookingFound() {
        when(bowlingBookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        BowlingBookingDTO dto = new BowlingBookingDTO(
                null, null, "newEmail@test.com", null, null, null, null
        );

        var result = bowlingBookingService.updatePartialBowlingBooking(1L, dto);

        assertNull(result);
    }

    @Test
    void updatePartialBowlingBookingDoesNotUpdateWhenFieldNotFound() {
        BowlingBooking booking = mock(BowlingBooking.class);
        when(bowlingBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        BowlingBookingDTO dto = new BowlingBookingDTO(
                null, null, null, null, null, null, null
        );

        bowlingBookingService.updatePartialBowlingBooking(1L, dto);

        verify(booking, never()).setCustomerEmail(any());
    }

    @Test
    void updatePartialBowlingBookingRemovesBookingWhenStatusIsCancelled() {
        BowlingBooking booking = mock(BowlingBooking.class);
        BowlingLane lane = mock(BowlingLane.class);
        when(booking.getLane()).thenReturn(lane);
        when(bowlingBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        BowlingBookingDTO dto = new BowlingBookingDTO(
                null, null, null, null, null, Status.CANCELLED, null
        );

        bowlingBookingService.updatePartialBowlingBooking(1L, dto);

        verify(lane).removeBooking(booking);
        verify(bowlingBookingRepository).save(any());
    }

    @Test
    void updatePartialBowlingBookingRemovesBookingWhenStatusIsNoShow() {
        BowlingBooking booking = mock(BowlingBooking.class);
        BowlingLane lane = mock(BowlingLane.class);
        when(booking.getLane()).thenReturn(lane);
        when(bowlingBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        BowlingBookingDTO dto = new BowlingBookingDTO(
                null, null, null, null, null, Status.NO_SHOW, null
        );

        bowlingBookingService.updatePartialBowlingBooking(1L, dto);

        verify(lane).removeBooking(booking);
        verify(bowlingBookingRepository).save(any());
    }

    @Test
    void updatePartialBowlingBookingThrowsExceptionWhenNoLanesAvailable() {
        BowlingBooking booking = mock(BowlingBooking.class);
        when(bowlingBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(bowlingLaneService.findFirstAvailableBowlingLane(any(), any(), anyBoolean())).thenReturn(null);

        BowlingBookingDTO dto = new BowlingBookingDTO(
                null, null, null, LocalDateTime.now(), LocalDateTime.now().plusHours(1), null, null
        );

        assertThrows(IllegalArgumentException.class, () -> bowlingBookingService.updatePartialBowlingBooking(1L, dto));
    }

    @Test
    void updatePartialBowlingBookingDoesNotSaveWhenNoFieldsUpdated() {
        BowlingBooking booking = mock(BowlingBooking.class);
        when(bowlingBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        BowlingBookingDTO dto = new BowlingBookingDTO(
                null, null, null, null, null, null, null
        );

        bowlingBookingService.updatePartialBowlingBooking(1L, dto);

        verify(bowlingBookingRepository, never()).save(any());
    }
}