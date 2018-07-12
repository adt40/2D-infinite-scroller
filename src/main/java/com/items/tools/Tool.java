package main.java.com.items.tools;

import main.java.com.items.InventoryItem;

public abstract class Tool extends InventoryItem {
    private Double baseDamage;
    private Double baseMiningSpeed;

    Tool(Double baseDamage, Double baseMiningSpeed, String name) {
        super(name);
        this.baseDamage = baseDamage;
        this.baseMiningSpeed = baseMiningSpeed;
    }

    public Double getBaseDamage() {
        return baseDamage;
    }

    public Double getBaseMiningSpeed() {
        return baseMiningSpeed;
    }
}
