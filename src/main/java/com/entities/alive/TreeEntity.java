package main.java.com.entities.alive;

import main.java.com.entities.materials.WoodenEntity;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TreeEntity extends WoodenEntity {

    private static final List<TileType> SPAWNABLE_TILES = Collections.singletonList(TileType.FOREST);
    private static final List<TileType> WALKABLE_TILES = Collections.emptyList(); //Trees can't walk silly
    private static final Double SPAWN_PROBABILITY = 0.25;

    public TreeEntity(Vector position) {
        super(position, SPAWNABLE_TILES, WALKABLE_TILES);
        Tile tile = Terrain.grid.get(position);
        tile.addOccupier(this);
    }

    //more likely to spawn if there are nearby trees
    public static boolean shouldSpawn(TileType tileType, Vector gridCoordinate) {
        if (Terrain.getAllAdjacentTilesTo(gridCoordinate).size() > 2) {
            return SPAWNABLE_TILES.contains(tileType) && (new Random()).nextDouble() <= SPAWN_PROBABILITY * 2;
        } else {
            return SPAWNABLE_TILES.contains(tileType) && (new Random()).nextDouble() <= SPAWN_PROBABILITY;
        }
    }

    @Override
    public void paint(Graphics g, int xPos, int yPos, int gridSize) {
        g.setColor(new Color(0, 214, 68));
        int size = (int)(gridSize * 0.75);
        int offset = (gridSize - size) / 2;
        g.fillRect(xPos + offset, yPos + offset, size, size);
    }

}
