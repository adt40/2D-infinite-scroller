package main.java.com.entities;

import main.java.com.ai.FlierAI;
import main.java.com.items.InventoryItem;
import main.java.com.items.tools.Bow;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class Flier extends NonPlayerEntity {

    private static final List<TileType> TILE_TYPES = Arrays.asList(TileType.WATER, TileType.GRASS1, TileType.GRASS2, TileType.GRASS3, TileType.FOREST, TileType.MOUNTAIN);
    private static final Double SPAWN_PROBABILITY = 0.005;

    public Flier(Vector position) {
        super(position, TILE_TYPES);

        Tile tile = Terrain.grid.get(position);
        tile.addOccupier(this);

        Timer timer = new Timer();
        FlierAI flierAI = new FlierAI(this);
        Random random = new Random();
        int period = random.nextInt(750) + 250;
        timer.schedule(flierAI, 500, period);
    }

    public static boolean shouldSpawn(TileType tileType, Vector gridCoordinate) {
        return TILE_TYPES.contains(tileType) && (new Random()).nextDouble() <= SPAWN_PROBABILITY;
    }

    @Override
    public void paint(Graphics g, int xPos, int yPos, int gridSize) {
        g.setColor(new Color(126, 55, 72));
        g.fillOval(xPos, yPos, gridSize, gridSize);
    }

    @Override
    public boolean click() {
        InventoryItem item = EntityManager.player.getSelectedItem();
        Double distance = EntityManager.player.getGridPosition().distanceTo(getGridPosition());
        if (item instanceof Bow && distance <= ((Bow) item).getRange()) {
            Tile tile = Terrain.grid.get(getGridPosition());
            tile.removeOccupier(this);
            EntityManager.nonPlayerEntities.remove(this);
            return true;
        }
        return false;
    }
}
