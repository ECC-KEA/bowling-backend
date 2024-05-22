package dk.ecc.bowlinghall.admin.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InventoryItemController {
    private final InventoryItemService inventoryItemService;

    public InventoryItemController(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<InventoryItemDTO>> getInventoryItems() {
        return ResponseEntity.ok(inventoryItemService.getInventoryItems());
    }

    @GetMapping("/inventory/{id}")
    public ResponseEntity<InventoryItemDTO> getInventoryItem(@PathVariable Long id) {
        return ResponseEntity.of(inventoryItemService.getInventoryItem(id));
    }

    @PostMapping("/inventory")
    public ResponseEntity<InventoryItemDTO> addInventoryItem(@RequestBody InventoryItemDTO inventoryItemDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryItemService.addInventoryItem(inventoryItemDTO));
    }

    @PatchMapping("/inventory/{id}")
    public ResponseEntity<InventoryItemDTO> updateInventoryItem(@PathVariable Long id, @RequestBody InventoryItemDTO inventoryItemDTO) {
        return ResponseEntity.ok(inventoryItemService.updateInventoryItem(id, inventoryItemDTO));
    }

    @DeleteMapping("/inventory/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long id) {
        inventoryItemService.deleteInventoryItem(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
