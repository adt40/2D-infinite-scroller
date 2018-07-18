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

public class FlierEntity extends NonPlayerEntity {

    private static final List<TileType> SPAWNABLE_TILES = Arrays.asList(TileType.WATER, TileType.GRASS1, TileType.GRASS2, TileType.GRASS3, TileType.FOREST, TileType.MOUNTAIN);
    private static final List<TileType> WALKABLE_TILES = Arrays.asList(TileType.WATER, TileType.GRASS1, TileType.GRASS2, TileType.GRASS3, TileType.FOREST, TileType.MOUNTAIN);
    private static final Double SPAWN_PROBABILITY = 0.005;

    private Timer timer;

    public FlierEntity(Vector position) {
        super(position, SPAWNABLE_TILES, WALKABLE_TILES);

        Tile tile = Terrain.grid.get(position);
        tile.addOccupier(this);

        timer = new Timer();
        FlierAI flierAI = new FlierAI(this);
        Random random = new Random();
        int period = random.nextInt(750) + 250;
        timer.schedule(flierAI, 500, period);
    }

    public static boolean shouldSpawn(TileType tileType, Vector gridCoordinate) {
        return SPAWNABLE_TILES.contains(tileType) && (new Random()).nextDouble() <= SPAWN_PROBABILITY;
    }

    @Override
    public void paint(Graphics g, int xPos, int yPos, int gridSize) {
        g.setColor(new Color(126, 55, 72));
        g.fillOval(xPos, yPos, gridSize, gridSize);
    }

    @Override
    public boolean click() {
        InventoryItem item = EntityManager.player.getSelectedItem();
        if (item instanceof Bow && ((Bow) item).isWithinRange(getGridPosition())) {
            Tile tile = Terrain.grid.get(getGridPosition());
            tile.removeOccupier(this);
            EntityManager.nonPlayerEntities.remove(this);
            timer.cancel();
            return true;
        }
        return false;
    }
}
