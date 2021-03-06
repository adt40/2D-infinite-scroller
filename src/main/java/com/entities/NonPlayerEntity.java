package main.java.com.entities;

import main.java.com.items.InventoryItem;
import main.java.com.items.tools.Tool;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.util.List;

public abstract class NonPlayerEntity extends Entity {

    private Class<? extends Tool> effectiveTool;

    public NonPlayerEntity(Vector position, List<TileType> spawnableTileTypes, List<TileType> walkableTileTypes, Integer maxHealth, Class<? extends Tool> effectiveTool) {
        super(position, spawnableTileTypes, walkableTileTypes, maxHealth);
        this.effectiveTool = effectiveTool;
    }

    public boolean isTrapped() {
        Vector up = getGridPosition().add(new Vector(0, -1));
        Vector left = getGridPosition().add(new Vector(-1, 0));
        Vector down = getGridPosition().add(new Vector(0, 1));
        Vector right = getGridPosition().add(new Vector(1, 0));

        return !((Terrain.grid.get(up) != null && isTileWalkable(Terrain.grid.get(up))) ||
                (Terrain.grid.get(left) != null && isTileWalkable(Terrain.grid.get(left))) ||
                (Terrain.grid.get(down) != null && isTileWalkable(Terrain.grid.get(down))) ||
                (Terrain.grid.get(right) != null && isTileWalkable(Terrain.grid.get(right))));
    }

    /**
     *
     * @return true if this entity was removed as a result of the click
     */
    public boolean click() {
        InventoryItem item = EntityManager.player.getSelectedItem();
        if (Tool.class.isAssignableFrom(item.getClass())) {
            if (Tool.class.cast(item).isWithinRange(getGridPosition())) {
                if (item.getClass().getName().equals(effectiveTool.getName())) {
                    dealDamage(Tool.class.cast(item).getMaxDamage());
                } else {
                    dealDamage(Tool.class.cast(item).getMinDamage());
                }
            }
        }
        if (isDead()) {
            doOnDeath();
            return true;
        }
        return false;
    }

    protected void remove() {
        Tile tile = Terrain.grid.get(getGridPosition());
        EntityManager.nonPlayerEntities.remove(this);
        tile.removeOccupier(this);
    }

    //Override for more functionality
    protected void doOnDeath() {
        remove();
    };

}
