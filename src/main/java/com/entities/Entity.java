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
    private final List<TileType> walkableTileTypes;

    Entity(Vector position, List<TileType> walkableTileTypes) {
        this.position = position;
        this.walkableTileTypes = walkableTileTypes;
    }

    public Vector getGridPosition() {
        return position;
    }

    void setGridPosition(Vector gridPosition) {
        this.position = gridPosition;
    }

    public List<TileType> getWalkableTileTypes() {
        return walkableTileTypes;
    }

    void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean hasChanged() {
        if (moved) {
            moved = false;
            return true;
        } else {
            return false;
        }
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
            tile.setOccupiedBy(null);
            nextTile.setOccupiedBy(this);
            position = nextPosition;
            moved = true;
        }
    }

    public abstract void paint(Graphics g, int xPos, int yPos, int gridSize);
}