package main.java.com.entities;

import main.java.com.items.InventoryItem;
import main.java.com.items.tools.Axe;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tree extends NonPlayerEntity {

    private static final List<TileType> TILE_TYPES = Collections.singletonList(TileType.FOREST);
    private static final Double SPAWN_PROBABILITY = 0.25;

    public Tree(Vector position) {
        super(position, TILE_TYPES);
        Tile tile = Terrain.grid.get(position);
        tile.addOccupier(this);
    }

    //more likely to spawn if there are nearby trees
    public static boolean shouldSpawn(TileType tileType, Vector gridCoordinate) {
        if (Terrain.getAllAdjacentTilesTo(gridCoordinate).size() > 2) {
            return TILE_TYPES.contains(tileType) && (new Random()).nextDouble() <= SPAWN_PROBABILITY * 2;
        } else {
            return TILE_TYPES.contains(tileType) && (new Random()).nextDouble() <= SPAWN_PROBABILITY;
        }
    }

    @Override
    public void paint(Graphics g, int xPos, int yPos, int gridSize) {
        g.setColor(new Color(0, 214, 68));
        int size = (int)(gridSize * 0.75);
        int offset = (gridSize - size) / 2;
        g.fillRect(xPos + offset, yPos + offset, size, size);
    }

    @Override
    public boolean click() {
        InventoryItem item = EntityManager.player.getSelectedItem();
        Double distance = EntityManager.player.getGridPosition().distanceTo(getGridPosition());
        if (item instanceof Axe && distance <= 1) {
            Tile tile = Terrain.grid.get(getGridPosition());
            tile.removeOccupier(this);
            EntityManager.nonPlayerEntities.remove(this);
            EntityManager.droppableEntities.add(new WoodEntity(getGridPosition()));
            return true;
        }
        return false;
    }
}
