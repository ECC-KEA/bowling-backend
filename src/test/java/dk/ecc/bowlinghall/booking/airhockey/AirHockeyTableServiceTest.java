package dk.ecc.bowlinghall.booking.airhockey;

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

class AirHockeyTableServiceTest {

    @Mock
    private AirHockeyTableRepository airHockeyTableRepository;

    private AirHockeyTableService airHockeyTableService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        airHockeyTableService = new AirHockeyTableService(airHockeyTableRepository);
    }

    @Test
    void findFirstAvailableAirHockeyTableReturnsTableWhenAvailable() {
        AirHockeyTable table = mock(AirHockeyTable.class);
        when(table.isAvailable(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(true);
        when(airHockeyTableRepository.findAll()).thenReturn(Collections.singletonList(table));

        AirHockeyTable result = airHockeyTableService.findFirstAvailableAirHockeyTable(LocalDateTime.now(), LocalDateTime.now().plusHours(2));

        assertEquals(table, result);
    }

    @Test
    void findFirstAvailableAirHockeyTableThrowsWhenNoTablesAvailable() {
        AirHockeyTable table = mock(AirHockeyTable.class);
        when(table.isAvailable(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(false);
        when(airHockeyTableRepository.findAll()).thenReturn(Collections.singletonList(table));

        assertThrows(IllegalArgumentException.class, () -> airHockeyTableService.findFirstAvailableAirHockeyTable(LocalDateTime.now(), LocalDateTime.now().plusHours(2)));
    }

    @Test
    void findFirstAvailableAirHockeyTableReturnsFirstAvailableWhenMultipleTables() {
        AirHockeyTable table1 = mock(AirHockeyTable.class);
        when(table1.isAvailable(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(false);
        AirHockeyTable table2 = mock(AirHockeyTable.class);
        when(table2.isAvailable(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(true);
        when(airHockeyTableRepository.findAll()).thenReturn(Arrays.asList(table1, table2));

        AirHockeyTable result = airHockeyTableService.findFirstAvailableAirHockeyTable(LocalDateTime.now(), LocalDateTime.now().plusHours(2));

        assertEquals(table2, result);
    }

    @Test
    void getAirHockeyTablesFromRepository() {
        AirHockeyTable table = mock(AirHockeyTable.class);
        when(airHockeyTableRepository.findAll()).thenReturn(Collections.singletonList(table));

        assertEquals(Collections.singletonList(table), airHockeyTableService.getAirHockeyTables());
    }

    @Test
    void getAirHockeyTableByIdFromRepository() {
        AirHockeyTable table = mock(AirHockeyTable.class);
        when(airHockeyTableRepository.findById(1L)).thenReturn(java.util.Optional.of(table));

        assertEquals(table, airHockeyTableService.getAirHockeyTableById(1L));
    }

    @Test
    void saveAirHockeyTableToRepository() {
        AirHockeyTable table = mock(AirHockeyTable.class);
        when(airHockeyTableRepository.save(table)).thenReturn(table);

        assertEquals(table, airHockeyTableService.saveAirHockeyTable(table));
    }

    @Test
    void getAirHockeyTableByIdThrowsWhenTableNotFound() {
        when(airHockeyTableRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> airHockeyTableService.getAirHockeyTableById(1L));
    }
}