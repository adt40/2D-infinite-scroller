package main.java.com.entities;

import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.util.List;
import java.util.Timer;

public abstract class NonPlayerEntity extends Entity {

    public NonPlayerEntity(Vector position, List<TileType> spawnableTileTypes, List<TileType> walkableTileTypes) {
        super(position, spawnableTileTypes, walkableTileTypes);
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
    public abstract boolean click();

    protected void remove(Timer timer) {
        Tile tile = Terrain.grid.get(getGridPosition());
        EntityManager.nonPlayerEntities.remove(this);
        tile.removeOccupier(this);
        timer.cancel();
    }
}
