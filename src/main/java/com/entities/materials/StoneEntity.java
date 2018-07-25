package main.java.com.entities.materials;

import main.java.com.entities.EntityManager;
import main.java.com.entities.NonPlayerEntity;
import main.java.com.entities.droppable.StoneDropEntity;
import main.java.com.items.InventoryItem;
import main.java.com.items.tools.PickAxe;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.util.List;

public abstract class StoneEntity extends NonPlayerEntity {

    public StoneEntity(Vector position, List<TileType> spawnableTiles, List<TileType> walkableTiles, Integer maxHealth) {
        super(position, spawnableTiles, walkableTiles, maxHealth, PickAxe.class);
    }

    @Override
    protected void doOnDeath() {
        remove();
        EntityManager.droppableEntities.add(new StoneDropEntity(getGridPosition()));
    }
}
