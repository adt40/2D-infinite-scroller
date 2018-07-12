package main.java.com.entities;

import main.java.com.ai.WalkerAI;
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

public class Walker extends NonPlayerEntity {

    //accessed via reflection in NonPlayerEntity::shouldSpawn
    static final List<TileType> TILE_TYPES = Arrays.asList(TileType.GRASS1, TileType.GRASS2, TileType.GRASS3, TileType.FOREST);
    static final Double SPAWN_PROBABILITY = 0.01;

    public Walker(Vector position) {
        super(position, TILE_TYPES);

        Tile tile = Terrain.grid.get(position);
        tile.setOccupiedBy(this);

        Timer timer = new Timer();
        WalkerAI walkerAI = new WalkerAI(this);
        Random random = new Random();
        int period = random.nextInt(1000) + 500;
        timer.schedule(walkerAI, 500, period);
    }

    @Override
    public void paint(Graphics g, int xPos, int yPos, int gridSize) {
        g.setColor(Color.BLACK);
        g.fillOval(xPos, yPos, gridSize, gridSize);
    }

    @Override
    public void click() {
        InventoryItem item = InstantiatedEntities.player.getSelectedItem();
        Double distance = InstantiatedEntities.player.getGridPosition().distanceTo(getGridPosition());
        if (item instanceof Sword && distance <= 1) {
            Tile tile = Terrain.grid.get(getGridPosition());
            tile.setOccupiedBy(null);
            InstantiatedEntities.nonPlayerEntities.remove(this);
        }
    }
}
