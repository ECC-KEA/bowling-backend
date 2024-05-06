package dk.ecc.bowlinghall.admin.inventory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class InventoryItem {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private int quantity;
}
