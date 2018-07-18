package main.java.com.entities.alive;

import main.java.com.ai.ZombieAI;
import main.java.com.entities.EntityManager;
import main.java.com.entities.NonPlayerEntity;
import main.java.com.items.InventoryItem;
import main.java.com.items.tools.Sword;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class ZombieEntity extends NonPlayerEntity {

    private static final java.util.List<TileType> SPAWNABLE_TILES = Arrays.asList(TileType.GRASS1, TileType.GRASS2, TileType.GRASS3, TileType.FOREST);
    private static final List<TileType> WALKABLE_TILES = Arrays.asList(TileType.GRASS1, TileType.GRASS2, TileType.GRASS3, TileType.FOREST, TileType.MOUNTAIN);
    private static final Double SPAWN_PROBABILITY = 0.01;

    private Timer timer;
    private boolean isTargetting;

    public ZombieEntity(Vector position) {
        super(position, SPAWNABLE_TILES, WALKABLE_TILES);

        Tile tile = Terrain.grid.get(position);
        tile.addOccupier(this);

        timer = new Timer();
        ZombieAI zombieAI = new ZombieAI(this);
        Random random = new Random();
        int period = random.nextInt(1000) + 500;
        timer.schedule(zombieAI, 500, period);
    }

    public static boolean shouldSpawn(TileType tileType, Vector gridCoordinate) {
        return SPAWNABLE_TILES.contains(tileType) && (new Random()).nextDouble() <= SPAWN_PROBABILITY;
    }

    @Override
    public boolean click() {
        InventoryItem item = EntityManager.player.getSelectedItem();
        if (item instanceof Sword && ((Sword) item).isWithinRange(getGridPosition())) {
            remove(timer);
            return true;
        }
        return false;
    }

    @Override
    public void paint(Graphics g, int xPos, int yPos, int gridSize) {
        if (isTargetting) {
            g.setColor(new Color(162, 47, 0));
        } else {
            g.setColor(new Color(19, 71, 0));
        }
        g.fillOval(xPos, yPos, gridSize, gridSize);
    }

    public void setIsTargetting(boolean isTargetting) {
        this.isTargetting = isTargetting;
    }
}
