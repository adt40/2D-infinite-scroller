package main.java.com.terrain;

import java.awt.*;

public enum TileType {
    WATER,
    GRASS1,
    GRASS2,
    FOREST,
    GRASS3,
    MOUNTAIN;

    public static TileType getTileTypeFromNoiseValue(Double noiseValue) {
        if (noiseValue < getValueFromTileType(WATER)) {
            return WATER;
        } else if (noiseValue < getValueFromTileType(GRASS1)) {
            return GRASS1;
        } else if (noiseValue < getValueFromTileType(GRASS2)) {
            return GRASS2;
        } else if (noiseValue < getValueFromTileType(FOREST)) {
            return FOREST;
        } else if (noiseValue < getValueFromTileType(GRASS3)) {
            return GRASS3;
        } else {
            return MOUNTAIN;
        }
    }

    public static Double getValueFromTileType(TileType tileType) {
        switch (tileType) {
            case WATER:
                return 0.4;
            case GRASS1:
                return 0.5;
            case GRASS2:
                return 0.6;
            case FOREST:
                return 0.69;
            case GRASS3:
                return 0.75;
            case MOUNTAIN:
                return 1.0;
            default:
                return 0.0;
        }
    }

    public Color getColor() {
        if (this.equals(TileType.WATER)) {
            return Color.BLUE;
        } else if (this.equals(TileType.GRASS1)) {
            return new Color(0, 180, 0);
        } else if (this.equals(TileType.GRASS2)) {
            return new Color(15, 135, 12);
        } else if (this.equals(TileType.FOREST)) {
            return new Color(79, 103, 34);
        } else if (this.equals(TileType.GRASS3)) {
            return new Color(66, 145, 20);
        } else if (this.equals(TileType.MOUNTAIN)) {
            return new Color(131, 148, 108);
        } else {
            return Color.WHITE;
        }
    }
}
