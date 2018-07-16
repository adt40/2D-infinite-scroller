package main.java.com.items.tools;

import main.java.com.entities.EntityManager;
import main.java.com.items.InventoryItem;
import main.java.com.util.Vector;

public abstract class Tool extends InventoryItem {
    private Double baseDamage;
    private Double baseMiningSpeed;
    private Double range;

    Tool(Double baseDamage, Double baseMiningSpeed, Double range, String name) {
        super(name);
        this.baseDamage = baseDamage;
        this.baseMiningSpeed = baseMiningSpeed;
        this.range = range;
    }

    public Double getBaseDamage() {
        return baseDamage;
    }

    public Double getBaseMiningSpeed() {
        return baseMiningSpeed;
    }

    public Double getRange() {
        return range;
    }

    public boolean isWithinRange(Vector gridCoordinate) {
        Double distance = EntityManager.player.getGridPosition().distanceTo(gridCoordinate);
        return distance <= range;
    }
}
