package main.java.com.terrain;

import main.java.com.entities.Entity;
import main.java.com.entities.NonPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private final TileType tileType;
    private final Double noiseValue;
    private List<Entity> occupyingEntities;

    public Tile(Double noiseValue) {
        this.noiseValue = noiseValue;
        tileType = TileType.getTileTypeFromNoiseValue(noiseValue);
        occupyingEntities = new ArrayList<>();
    }

    public Double getNoiseValue() {
        return noiseValue;
    }

    public TileType getTileType() {
        return tileType;
    }

    public boolean isOccupied() {
        return occupyingEntities.size() != 0;
    }

    public <T extends NonPlayerEntity> boolean isOccupiedBy(Class<T> nonPlayerEntityClass) {
        return occupyingEntities.stream().anyMatch(entity -> entity.getClass() == nonPlayerEntityClass);
    }

    public void addOccupier(Entity entity) {
        occupyingEntities.add(entity);
    }

    public void removeOccupier(Entity entity) { occupyingEntities.remove(entity); }

    public List<Entity> getOccupyingEntities() {
        return occupyingEntities;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "tileType=" + tileType +
                ", noiseValue=" + noiseValue +
                ", occupyingEntities=" + occupyingEntities.toString() +
                '}';
    }
}
