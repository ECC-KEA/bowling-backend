package dk.ecc.bowlinghall.booking.bowling;

import dk.ecc.bowlinghall.booking.Status;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class BowlingBookingServiceTest {

    @Mock
    private BowlingBookingRepository bowlingBookingRepository;

    @Mock
    private BowlingLaneService bowlingLaneService;

    @InjectMocks
    private BowlingBookingService bowlingBookingService;

    @Test
    void updatePartialBowlingBookingUpdatesFieldsAndSavesBooking() {
        BowlingBooking booking = mock(BowlingBooking.class);
        when(bowlingBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        Map<String, Object> fields = new HashMap<>();
        fields.put("customerEmail", "newEmail@test.com");

        bowlingBookingService.updatePartialBowlingBooking(1L, fields);

        verify(bowlingBookingRepository).save(any());
    }

    @Test
    void updatePartialBowlingBookingReturnsNullWhenNoBookingFound() {
        when(bowlingBookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        Map<String, Object> fields = new HashMap<>();
        fields.put("customerEmail", "newEmail@test.com");

        var result = bowlingBookingService.updatePartialBowlingBooking(1L, fields);

        assertEquals(null, result);
    }

    @Test
    void updatePartialBowlingBookingDoesNotUpdateWhenFieldNotFound() {
        BowlingBooking booking = mock(BowlingBooking.class);
        when(bowlingBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        Map<String, Object> fields = new HashMap<>();
        fields.put("nonExistentField", "value");

        bowlingBookingService.updatePartialBowlingBooking(1L, fields);

        verify(bowlingBookingRepository, never()).save(any());
    }

    @Test
    void updatePartialBowlingBookingRemovesBookingWhenStatusIsCancelled() {
        BowlingBooking booking = mock(BowlingBooking.class);
        BowlingLane lane = mock(BowlingLane.class);
        when(booking.getLane()).thenReturn(lane);
        when(bowlingBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        Map<String, Object> fields = new HashMap<>();
        fields.put("status", Status.CANCELLED);

        bowlingBookingService.updatePartialBowlingBooking(1L, fields);

        verify(lane).removeBooking(booking);
        verify(bowlingBookingRepository).save(any());
    }

    @Test
    void updatePartialBowlingBookingRemovesBookingWhenStatusIsNoShow() {
        BowlingBooking booking = mock(BowlingBooking.class);
        BowlingLane lane = mock(BowlingLane.class);
        when(booking.getLane()).thenReturn(lane);
        when(bowlingBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        Map<String, Object> fields = new HashMap<>();
        fields.put("status", Status.NO_SHOW);

        bowlingBookingService.updatePartialBowlingBooking(1L, fields);

        verify(lane).removeBooking(booking);
        verify(bowlingBookingRepository).save(any());
    }

    @Test
    void updatePartialBowlingBookingThrowsExceptionWhenNoLanesAvailable() {
        BowlingBooking booking = mock(BowlingBooking.class);
        when(bowlingBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(bowlingLaneService.findFirstAvailableBowlingLane(any(), any(), anyBoolean())).thenReturn(null);

        Map<String, Object> fields = new HashMap<>();
        fields.put("start", LocalDateTime.now());
        fields.put("end", LocalDateTime.now().plusHours(1));

        assertThrows(IllegalArgumentException.class, () -> bowlingBookingService.updatePartialBowlingBooking(1L, fields));
    }

    @Test
    void updatePartialBowlingBookingDoesNotSaveWhenNoFieldsUpdated() {
        BowlingBooking booking = mock(BowlingBooking.class);
        when(bowlingBookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        Map<String, Object> fields = new HashMap<>();
        fields.put("nonExistentField", "value");

        bowlingBookingService.updatePartialBowlingBooking(1L, fields);

        verify(bowlingBookingRepository, never()).save(any());
    }
}