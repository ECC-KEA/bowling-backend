package dk.ecc.bowlinghall.admin.inventory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class InventoryItemControllerTest {

    @MockBean
    private InventoryItemService inventoryItemService;

    @Autowired
    private WebTestClient webClient;

    private InventoryItemDTO inventoryItemDTO;

    @BeforeEach
    public void setup() {
        inventoryItemDTO = new InventoryItemDTO(1L, "Item1", 10);
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setId(1L);
        inventoryItem.setName("Item1");
        inventoryItem.setQuantity(10);

        when(inventoryItemService.addInventoryItem(any(InventoryItemDTO.class))).thenReturn(inventoryItemDTO);
        when(inventoryItemService.getInventoryItem(1L)).thenReturn(Optional.of(inventoryItemDTO));
        when(inventoryItemService.getInventoryItems()).thenReturn(Arrays.asList(inventoryItemDTO));
    }

    @AfterEach
    public void cleanUp(@Autowired InventoryItemRepository inventoryItemRepository) {
        inventoryItemRepository.deleteAll();
        reset(inventoryItemService);
    }

    @Test
    public void addInventoryItem_returnsAddedItem() {
        webClient.post().uri("/inventory")
                .bodyValue(inventoryItemDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(InventoryItemDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(inventoryItemDTO, response);
                });
    }

    @Test
    public void getInventoryItem_returnsItem() {
        webClient.get().uri("/inventory/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(InventoryItemDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals("Item1", response.name());
                });
    }

    @Test
    public void getInventoryItems_returnsAllItems() {
        webClient.get().uri("/inventory")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(InventoryItemDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(1, response.size());
                });
    }

    @Test
    public void updateInventoryItem_updatesAndReturnsItem() {
        InventoryItemDTO updatedInventoryItemDTO = new InventoryItemDTO(1L, "UpdatedItem", 15);

        when(inventoryItemService.updateInventoryItem(1L, updatedInventoryItemDTO)).thenReturn(updatedInventoryItemDTO);

        webClient.patch().uri("/inventory/1")
                .bodyValue(updatedInventoryItemDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(InventoryItemDTO.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals("UpdatedItem", response.name());
                });
    }

    @Test
    public void deleteInventoryItem_deletesItem() {
        doNothing().when(inventoryItemService).deleteInventoryItem(1L);

        webClient.delete().uri("/inventory/1")
                .exchange()
                .expectStatus().isNoContent();
    }
}