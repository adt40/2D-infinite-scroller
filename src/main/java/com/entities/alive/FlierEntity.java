package main.java.com.entities.alive;

import main.java.com.ai.FlierAI;
import main.java.com.entities.NonPlayerEntity;
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

    private static final List<TileType> SPAWNABLE_TILES = Arrays.asList(TileType.WATER, TileType.GRASS1, TileType.GRASS2, TileType.GRASS3, TileType.FOREST);
    private static final List<TileType> WALKABLE_TILES = Arrays.asList(TileType.WATER, TileType.GRASS1, TileType.GRASS2, TileType.GRASS3, TileType.FOREST, TileType.MOUNTAIN);
    private static final Double SPAWN_PROBABILITY = 0.005;

    private Timer timer;

    public FlierEntity(Vector position) {
        super(position, SPAWNABLE_TILES, WALKABLE_TILES,30, Bow.class);

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
    public void paint(Graphics2D g, int xPos, int yPos, int gridSize) {
        g.setColor(new Color(126, 55, 72));
        g.fillOval(xPos, yPos, gridSize, gridSize);
        paintHealthBar(g, xPos, yPos, gridSize);
    }

    @Override
    protected void doOnDeath() {
        remove();
        timer.cancel();
    }
}
