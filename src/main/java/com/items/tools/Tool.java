package main.java.com.items.tools;

import main.java.com.entities.EntityManager;
import main.java.com.items.InventoryItem;
import main.java.com.util.Vector;

public abstract class Tool extends InventoryItem {
    private Integer minDamage;
    private Integer maxDamage;
    private Double range;

    Tool(Integer minDamage, Integer maxDamage, Double range, String name) {
        super(name);
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.range = range;
    }

    public Integer getMinDamage() { return minDamage; }

    public Integer getMaxDamage() { return maxDamage; }

    public Double getRange() {
        return range;
    }

    public boolean isWithinRange(Vector gridCoordinate) {
        Double distance = EntityManager.player.getGridPosition().distanceTo(gridCoordinate);
        return distance <= range;
    }
}
