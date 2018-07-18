package main.java.com.terrain;

import main.java.com.entities.*;
import main.java.com.entities.alive.*;
import main.java.com.entities.materials.MountainStoneBlockEntity;
import main.java.com.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Terrain {
    public static final HashMap<Vector, Tile> grid = new HashMap<>();

    /**
     * Gets all tiles directly adjacent (4 cardinal directions) to the provided grid location.
     * @param gridCoordinate location in the world
     * @return List of directly adjacent tiles
     */
    public static List<Tile> getDirectlyAdjacentTilesTo(Vector gridCoordinate) {
        List<Tile> adjacents = new ArrayList<>();
        adjacents.add(grid.get(gridCoordinate.add(new Vector(1, 0))));
        adjacents.add(grid.get(gridCoordinate.add(new Vector(-1, 0))));
        adjacents.add(grid.get(gridCoordinate.add(new Vector(0, 1))));
        adjacents.add(grid.get(gridCoordinate.add(new Vector(0, -1))));
        return adjacents;
    }

    /**
     * Gets all tiles adjacent (4 cardinal directions and the diagonals) to the provided grid location.
     * @param gridCoordinate location in the world
     * @return List of all adjacent tiles
     */
    public static List<Tile> getAllAdjacentTilesTo(Vector gridCoordinate) {
        List<Tile> adjacents = getDirectlyAdjacentTilesTo(gridCoordinate);
        adjacents.add(grid.get(gridCoordinate.add(new Vector(1, 1))));
        adjacents.add(grid.get(gridCoordinate.add(new Vector(1, -1))));
        adjacents.add(grid.get(gridCoordinate.add(new Vector(-1, 1))));
        adjacents.add(grid.get(gridCoordinate.add(new Vector(-1, -1))));
        return adjacents;
    }

    public static void spawnEntities(TileType tileType, Vector gridCoordinate) {
        if (WalkerEntity.shouldSpawn(tileType, gridCoordinate)) {
            EntityManager.nonPlayerEntities.add(new WalkerEntity(gridCoordinate));
        } else if (FlierEntity.shouldSpawn(tileType, gridCoordinate)) {
            EntityManager.nonPlayerEntities.add(new FlierEntity(gridCoordinate));
        } else if (TreeEntity.shouldSpawn(tileType, gridCoordinate)) {
            EntityManager.nonPlayerEntities.add(new TreeEntity(gridCoordinate));
        } else if (MountainStoneBlockEntity.shouldSpawn(tileType, gridCoordinate)) {
            EntityManager.nonPlayerEntities.add(new MountainStoneBlockEntity(gridCoordinate));
        } else if (ZombieEntity.shouldSpawn(tileType, gridCoordinate)) {
            EntityManager.nonPlayerEntities.add(new ZombieEntity(gridCoordinate));
        } else if (FishEntity.shouldSpawn(tileType, gridCoordinate)) {
            EntityManager.nonPlayerEntities.add(new FishEntity(gridCoordinate));
        }
    }
}
