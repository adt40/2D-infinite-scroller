package main.java.com.terrain;

import main.java.com.entities.*;
import main.java.com.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Terrain {
    public static final HashMap<Vector, Tile> grid = new HashMap<>();

    /**
     * Gets all tiles directly adjacent (4 cardinal directions) to the provided grid location.
     * @param gridLocation location in the world
     * @return List of directly adjacent tiles
     */
    public static List<Tile> getDirectlyAdjacentTilesTo(Vector gridLocation) {
        List<Tile> adjacents = new ArrayList<>();
        adjacents.add(grid.get(gridLocation.add(new Vector(1, 0))));
        adjacents.add(grid.get(gridLocation.add(new Vector(-1, 0))));
        adjacents.add(grid.get(gridLocation.add(new Vector(0, 1))));
        adjacents.add(grid.get(gridLocation.add(new Vector(0, -1))));
        return adjacents;
    }

    /**
     * Gets all tiles adjacent (4 cardinal directions and the diagonals) to the provided grid location.
     * @param gridLocation location in the world
     * @return List of all adjacent tiles
     */
    public static List<Tile> getAllAdjacentTilesTo(Vector gridLocation) {
        List<Tile> adjacents = getDirectlyAdjacentTilesTo(gridLocation);
        adjacents.add(grid.get(gridLocation.add(new Vector(1, 1))));
        adjacents.add(grid.get(gridLocation.add(new Vector(1, -1))));
        adjacents.add(grid.get(gridLocation.add(new Vector(-1, 1))));
        adjacents.add(grid.get(gridLocation.add(new Vector(-1, -1))));
        return adjacents;
    }

    public static void spawnEntities(TileType tileType, Vector gridCoordinate) {
        if (Walker.shouldSpawn(tileType, gridCoordinate)) {
            EntityManager.nonPlayerEntities.add(new Walker(gridCoordinate));
        } else if (Flier.shouldSpawn(tileType, gridCoordinate)) {
            EntityManager.nonPlayerEntities.add(new Flier(gridCoordinate));
        } else if (Tree.shouldSpawn(tileType, gridCoordinate)) {
            EntityManager.nonPlayerEntities.add(new Tree(gridCoordinate));
        }
    }
}
