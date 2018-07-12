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

    //accessed via reflection in NonPlayerEntity::shouldSpawn
    static final List<TileType> TILE_TYPES = Arrays.asList(TileType.WATER, TileType.GRASS1, TileType.GRASS2, TileType.GRASS3, TileType.FOREST, TileType.MOUNTAIN);
    static final Double SPAWN_PROBABILITY = 0.005;

    public Flier(Vector position) {
        super(position, TILE_TYPES);
        Timer timer = new Timer();
        FlierAI flierAI = new FlierAI(this);
        Random random = new Random();
        int period = random.nextInt(750) + 250;
        timer.schedule(flierAI, 500, period);
    }

    @Override
    public void paint(Graphics g, int xPos, int yPos, int gridSize) {
        g.setColor(new Color(126, 55, 72));
        g.fillOval(xPos, yPos, gridSize, gridSize);
    }

    @Override
    public void click() {
        InventoryItem item = InstantiatedEntities.player.getSelectedItem();
        Double distance = InstantiatedEntities.player.getGridPosition().distanceTo(getGridPosition());
        if (item instanceof Bow && distance <= ((Bow) item).getRange()) {
            Tile tile = Terrain.grid.get(getGridPosition());
            tile.setOccupiedBy(null);
            InstantiatedEntities.nonPlayerEntities.remove(this);
        }
    }
}
