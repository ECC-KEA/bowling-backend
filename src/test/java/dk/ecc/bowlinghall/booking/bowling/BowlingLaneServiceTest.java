package dk.ecc.bowlinghall.booking.bowling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BowlingLaneServiceTest {

    @Mock
    private BowlingLaneRepository bowlingLaneRepository;

    private BowlingLaneService bowlingLaneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bowlingLaneService = new BowlingLaneService(bowlingLaneRepository);
    }

    @Test
    void findFirstAvailableBowlingLaneReturnsLaneWhenAvailable() {
        BowlingLane lane = mock(BowlingLane.class);
        when(lane.isAvailable(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(true);
        when(lane.isChildFriendly()).thenReturn(true);
        when(bowlingLaneRepository.findAll()).thenReturn(Collections.singletonList(lane));

        BowlingLane result = bowlingLaneService.findFirstAvailableBowlingLane(LocalDateTime.now(), LocalDateTime.now().plusHours(2), true);

        assertEquals(lane, result);
    }

    @Test
    void findFirstAvailableBowlingLaneThrowsWhenNoLanesAvailable() {
        BowlingLane lane = mock(BowlingLane.class);
        when(lane.isAvailable(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(false);
        when(bowlingLaneRepository.findAll()).thenReturn(Collections.singletonList(lane));

        assertThrows(IllegalArgumentException.class, () -> bowlingLaneService.findFirstAvailableBowlingLane(LocalDateTime.now(), LocalDateTime.now().plusHours(2), true));
    }

    @Test
    void findFirstAvailableBowlingLaneReturnsFirstAvailableWhenMultipleLanes() {
        BowlingLane lane1 = mock(BowlingLane.class);
        when(lane1.isAvailable(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(false);
        BowlingLane lane2 = mock(BowlingLane.class);
        when(lane2.isAvailable(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(true);
        when(lane2.isChildFriendly()).thenReturn(true);

        when(bowlingLaneRepository.findAll()).thenReturn(Arrays.asList(lane1, lane2));

        BowlingLane result = bowlingLaneService.findFirstAvailableBowlingLane(LocalDateTime.now(), LocalDateTime.now().plusHours(2), true);

        assertEquals(lane2, result);
    }

    @Test
    void getBowlingLanesReturnsLanesFromRepository() {
        BowlingLane lane = mock(BowlingLane.class);
        when(bowlingLaneRepository.findAll()).thenReturn(Collections.singletonList(lane));

        assertEquals(Collections.singletonList(lane), bowlingLaneService.getBowlingLanes());
    }

    @Test
    void getBowlingLaneByIdReturnsLaneFromRepository() {
        BowlingLane lane = mock(BowlingLane.class);
        when(bowlingLaneRepository.findById(1L)).thenReturn(java.util.Optional.of(lane));

        assertEquals(lane, bowlingLaneService.getBowlingLaneById(1L));
    }

    @Test
    void saveBowlingLaneSavesLaneToRepository() {
        BowlingLane lane = mock(BowlingLane.class);
        when(bowlingLaneRepository.save(lane)).thenReturn(lane);

        assertEquals(lane, bowlingLaneService.saveBowlingLane(lane));
    }

    @Test
    void getBowlingLaneByIdThrowsWhenNotFound() {
        when(bowlingLaneRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> bowlingLaneService.getBowlingLaneById(1L));
    }


}