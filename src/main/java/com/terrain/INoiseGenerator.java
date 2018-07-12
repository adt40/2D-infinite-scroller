package main.java.com.terrain;

import main.java.com.util.Vector;

public interface INoiseGenerator {
    Double noiseAt(Vector gridCoordinate);
}
