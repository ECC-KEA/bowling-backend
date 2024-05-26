package dk.ecc.bowlinghall.admin.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class InventoryItemServiceTest {

    @MockBean
    private InventoryItemRepository inventoryItemRepository;

    @MockBean
    private CacheManager cacheManager;

    @Autowired
    private InventoryItemService inventoryItemService;

    private Cache cache;

    @BeforeEach
    public void setup() {
        cache = mock(Cache.class);
        when(cacheManager.getCache("inventoryItems")).thenReturn(cache);
    }

    @Test
    public void addInventoryItem_returnsAddedItem() {
        InventoryItemDTO inventoryItemDTO = new InventoryItemDTO(1L, "Item1", 10);
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setId(1L);
        inventoryItem.setName("Item1");
        inventoryItem.setQuantity(10);

        when(inventoryItemRepository.save(any(InventoryItem.class))).thenReturn(inventoryItem);

        InventoryItemDTO result = inventoryItemService.addInventoryItem(inventoryItemDTO);

        assertEquals(inventoryItemDTO, result);
        verify(cache).clear();
    }

    @Test
    public void getInventoryItem_returnsItem() {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setId(1L);
        inventoryItem.setName("Item1");
        inventoryItem.setQuantity(10);

        when(inventoryItemRepository.findById(1L)).thenReturn(Optional.of(inventoryItem));

        Optional<InventoryItemDTO> result = inventoryItemService.getInventoryItem(1L);

        assertEquals("Item1", result.get().name());
    }

    @Test
    public void getInventoryItems_returnsAllItems() {
        InventoryItem inventoryItem1 = new InventoryItem();
        inventoryItem1.setId(1L);
        inventoryItem1.setName("Item1");
        inventoryItem1.setQuantity(10);

        InventoryItem inventoryItem2 = new InventoryItem();
        inventoryItem2.setId(2L);
        inventoryItem2.setName("Item2");
        inventoryItem2.setQuantity(20);

        when(inventoryItemRepository.findAll()).thenReturn(Arrays.asList(inventoryItem1, inventoryItem2));

        List<InventoryItemDTO> result = inventoryItemService.getInventoryItems();

        assertEquals(2, result.size());
        verify(cache).put(any(), eq(result));
    }

    @Test
    public void updateInventoryItem_updatesAndReturnsItem() {
        InventoryItemDTO inventoryItemDTO = new InventoryItemDTO(1L, "UpdatedItem", 15);
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setId(1L);
        inventoryItem.setName("Item1");
        inventoryItem.setQuantity(10);

        when(inventoryItemRepository.findById(1L)).thenReturn(Optional.of(inventoryItem));
        when(inventoryItemRepository.save(any(InventoryItem.class))).thenReturn(inventoryItem);

        InventoryItemDTO result = inventoryItemService.updateInventoryItem(1L, inventoryItemDTO);

        assertEquals("UpdatedItem", result.name());
        verify(cache).clear();
    }

    @Test
    public void deleteInventoryItem_deletesItem() {
        doNothing().when(inventoryItemRepository).deleteById(1L);

        inventoryItemService.deleteInventoryItem(1L);

        verify(inventoryItemRepository, times(1)).deleteById(1L);
        verify(cache).clear();
    }
}
