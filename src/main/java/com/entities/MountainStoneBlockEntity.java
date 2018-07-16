package main.java.com.entities;

import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MountainStoneBlockEntity extends StoneEntity {

    private static final List<TileType> SPAWNABLE_TILES = Collections.singletonList(TileType.MOUNTAIN);
    private static final List<TileType> WALKABLE_TILES = Collections.emptyList();
    private static final Double SPAWN_PROBABILITY = 1.0;

    public MountainStoneBlockEntity(Vector position) {
        super(position, SPAWNABLE_TILES, WALKABLE_TILES);
        Tile tile = Terrain.grid.get(position);
        tile.addOccupier(this);
    }

    public static boolean shouldSpawn(TileType tileType, Vector gridCoordinate) {
        return SPAWNABLE_TILES.contains(tileType) && (new Random()).nextDouble() <= SPAWN_PROBABILITY;
    }

    @Override
    public void paint(Graphics g, int xPos, int yPos, int gridSize) {
        g.setColor(new Color(167, 167, 165));
        g.fillRect(xPos + 1, yPos + 1, gridSize - 2, gridSize - 2);
    }
}
