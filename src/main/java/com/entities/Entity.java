package main.java.com.entities;

import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.awt.*;
import java.util.List;

public abstract class Entity {

    private Vector position;
    private boolean moved;
    private Integer maxHealth;
    private Integer currentHealth;
    private final List<TileType> spawnableTileTypes;
    private final List<TileType> walkableTileTypes;

    public Entity(Vector position, List<TileType> spawnableTileTypes, List<TileType> walkableTileTypes, Integer maxHealth) {
        this.position = position;
        this.spawnableTileTypes = spawnableTileTypes;
        this.walkableTileTypes = walkableTileTypes;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public Vector getGridPosition() {
        return position;
    }

    public void setGridPosition(Vector gridPosition) {
        this.position = gridPosition;
    }

    public List<TileType> getSpawnableTileTypes() { return spawnableTileTypes; }

    public List<TileType> getWalkableTileTypes() {
        return walkableTileTypes;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public Integer getMaxHealth() {
        return maxHealth;
    }

    public Integer getCurrentHealth() {
        return currentHealth;
    }

    public void dealDamage(Integer amount) { currentHealth -= amount; }

    public boolean isDead() { return currentHealth <= 0; }

    public boolean hasChanged() {
        if (moved) {
            moved = false;
            return true;
        } else {
            return false;
        }
    }

    public boolean isTileSpawnable(Tile tile) {
        if (tile != null) {
            return spawnableTileTypes.contains(tile.getTileType()) && !tile.isOccupied();
        }
        return false;
    }

    public boolean isTileWalkable(Tile tile) {
        if (tile != null) {
            return walkableTileTypes.contains(tile.getTileType()) && !tile.isOccupied();
        }
        return false;
    }

    public void move(Vector direction) {
        Vector nextPosition = position.add(direction);
        Tile nextTile = Terrain.grid.get(nextPosition);
        if (isTileWalkable(nextTile)) {
            Tile tile = Terrain.grid.get(position);
            tile.removeOccupier(this);
            nextTile.addOccupier(this);
            position = nextPosition;
            moved = true;
        }
    }

    public abstract void paint(Graphics2D g, int xPos, int yPos, int gridSize);

    protected void paintHealthBar(Graphics2D g, int xPos, int yPos, int gridSize) {
        if (!currentHealth.equals(maxHealth)) {
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(3));
            int offset = gridSize / 10;
            int x0 = xPos + offset;
            int x1 = xPos + gridSize - offset;
            int y = yPos - offset;
            g.drawLine(x0, y, x1, y);

            g.setColor(Color.RED);
            double percentHealthRemaining = 1 - (double)(maxHealth - currentHealth) / (double)maxHealth;
            int x2 = (int)((x1 - x0) * percentHealthRemaining) + x0;
            g.drawLine(x0, y, x2, y);
        }
    }
}
