package main.java.com.terrain;

import main.java.com.entities.Entity;

public class Tile {
    private final TileType tileType;
    private final Double noiseValue;
    private Entity occupyingEntity;

    public Tile(Double noiseValue) {
        this.noiseValue = noiseValue;
        tileType = TileType.getTileTypeFromNoiseValue(noiseValue);
    }

    public Double getNoiseValue() {
        return noiseValue;
    }

    public TileType getTileType() {
        return tileType;
    }

    public boolean isOccupied() {
        return occupyingEntity != null;
    }

    public void setOccupiedBy(Entity entity) {
        occupyingEntity = entity;
    }

    public Entity getOccupyingEntity() {
        return occupyingEntity;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "tileType=" + tileType +
                ", noiseValue=" + noiseValue +
                ", entityOnThis=" + occupyingEntity +
                '}';
    }
}
