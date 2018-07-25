package main.java.com.terrain;

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
}
