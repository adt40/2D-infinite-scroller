package main.java.com.entities;

import main.java.com.items.InventoryItem;
import main.java.com.items.tools.PickAxe;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.util.List;

public abstract class StoneEntity extends NonPlayerEntity {

    public StoneEntity(Vector position, List<TileType> spawnableTiles, List<TileType> walkableTiles) {
        super(position, spawnableTiles, walkableTiles);
    }

    @Override
    public boolean click() {
        InventoryItem item = EntityManager.player.getSelectedItem();
        if (item instanceof PickAxe && ((PickAxe) item).isWithinRange(getGridPosition())) {
            Tile tile = Terrain.grid.get(getGridPosition());
            tile.removeOccupier(this);
            EntityManager.nonPlayerEntities.remove(this);
            EntityManager.droppableEntities.add(new StoneDropEntity(getGridPosition()));
            return true;
        }
        return false;
    }
}
