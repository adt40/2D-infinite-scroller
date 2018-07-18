package main.java.com.entities.materials;

import main.java.com.entities.EntityManager;
import main.java.com.entities.NonPlayerEntity;
import main.java.com.entities.droppable.WoodDropEntity;
import main.java.com.items.InventoryItem;
import main.java.com.items.tools.Axe;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.util.List;

public abstract class WoodenEntity extends NonPlayerEntity {

    public WoodenEntity(Vector position, List<TileType> spawnableTiles, List<TileType> walkableTiles) {
        super(position, spawnableTiles, walkableTiles);
    }

    @Override
    public boolean click() {
        InventoryItem item = EntityManager.player.getSelectedItem();
        if (item instanceof Axe && ((Axe) item).isWithinRange(getGridPosition())) {
            Tile tile = Terrain.grid.get(getGridPosition());
            tile.removeOccupier(this);
            EntityManager.nonPlayerEntities.remove(this);
            EntityManager.droppableEntities.add(new WoodDropEntity(getGridPosition()));
            return true;
        }
        return false;
    }

}
