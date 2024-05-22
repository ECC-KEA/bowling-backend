package dk.ecc.bowlinghall.admin.inventory;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class InventoryItemService {

    private final InventoryItemRepository inventoryItemRepository;

    public InventoryItemService(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @CacheEvict(value = "inventoryItems", allEntries = true)
    public InventoryItemDTO addInventoryItem(InventoryItemDTO inventoryItemDTO) {
        return toDTO(inventoryItemRepository.save(toEntity(inventoryItemDTO)));
    }

    public Optional<InventoryItemDTO> getInventoryItem(Long id) {
        return Optional.of(toDTO(inventoryItemRepository.findById(id).orElseThrow()));
    }

    @Cacheable("inventoryItems")
    public List<InventoryItemDTO> getInventoryItems() {
        List<InventoryItem> inventoryItems = inventoryItemRepository.findAll();
        return inventoryItems.stream().map(this::toDTO).toList();
    }

    @CacheEvict(value = "inventoryItems", allEntries = true)
    public void deleteInventoryItem(Long id) {
        inventoryItemRepository.deleteById(id);
    }

    @CacheEvict(value = "inventoryItems", allEntries = true)
    public InventoryItemDTO updateInventoryItem(Long id, InventoryItemDTO inventoryItemDTO) {
        Map<String, Object> fields = toMap(inventoryItemDTO);
        Optional<InventoryItem> existingInventoryItem = inventoryItemRepository.findById(id);
        existingInventoryItem.ifPresent(inventoryItem -> fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(InventoryItem.class, key);
            if (field != null) {
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, inventoryItem, value);
            }
        }));
        return toDTO(inventoryItemRepository.save(existingInventoryItem.orElseThrow()));
    }

    private InventoryItemDTO toDTO(InventoryItem inventoryItem) {
        return new InventoryItemDTO(inventoryItem.getId(), inventoryItem.getName(), inventoryItem.getQuantity());
    }

    private InventoryItem toEntity(InventoryItemDTO inventoryItemDTO) {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setName(inventoryItemDTO.name());
        inventoryItem.setQuantity(inventoryItemDTO.quantity());
        return inventoryItem;
    }

    private Map<String, Object> toMap(InventoryItemDTO inventoryItemDTO) {
        Map<String, Object> map = new HashMap<>();
        if (inventoryItemDTO.id() != null) {
            map.put("id", inventoryItemDTO.id());
        }
        if (inventoryItemDTO.name() != null) {
            map.put("name", inventoryItemDTO.name());
        }
        if (inventoryItemDTO.quantity() != null) {
            map.put("quantity", inventoryItemDTO.quantity());
        }
        return map;
    }
}
