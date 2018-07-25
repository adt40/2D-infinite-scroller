package main.java.com.entities.alive;

import main.java.com.ai.FishAI;
import main.java.com.entities.EntityManager;
import main.java.com.entities.NonPlayerEntity;
import main.java.com.items.InventoryItem;
import main.java.com.items.tools.FishingRod;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.awt.*;
import java.util.*;
import java.util.List;

public class FishEntity extends NonPlayerEntity {

    private static final List<TileType> SPAWNABLE_TILES = Collections.singletonList(TileType.WATER);
    private static final List<TileType> WALKABLE_TILES = Collections.singletonList(TileType.WATER);
    private static final Double SPAWN_PROBABILITY = 0.01;

    private Timer timer;

    public FishEntity(Vector position) {
        super(position, SPAWNABLE_TILES, WALKABLE_TILES, 25, FishingRod.class);

        Tile tile = Terrain.grid.get(position);
        tile.addOccupier(this);

        timer = new Timer();
        FishAI fishAI = new FishAI(this);
        Random random = new Random();
        int period = random.nextInt(1000) + 500;
        timer.schedule(fishAI, 500, period);
    }

    public static boolean shouldSpawn(TileType tileType, Vector gridCoordinate) {
        return SPAWNABLE_TILES.contains(tileType) && (new Random()).nextDouble() <= SPAWN_PROBABILITY;
    }

    @Override
    public void paint(Graphics2D g, int xPos, int yPos, int gridSize) {
        g.setColor(new Color(0, 157, 161));
        g.fillOval(xPos, yPos, gridSize, gridSize);
        paintHealthBar(g, xPos, yPos, gridSize);
    }

    @Override
    protected void doOnDeath() {
        remove();
        timer.cancel();
    }
}
