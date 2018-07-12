package main.java.com.terrain;

import main.java.com.util.Vector;

import java.util.HashMap;
import java.util.Random;

public class PerlinNoise implements INoiseGenerator {

    private final Random rng;
    private final HashMap<Vector, Vector> gradient;

    public PerlinNoise() {
        rng = new Random();
        gradient = new HashMap<>();
    }

    public Double noiseAt(Vector gridCoordinate) {
        Vector v0 = gridCoordinate.roundedDown();
        Vector v1 = v0.add(new Vector(1.0, 1.0));
        addRandomGradientAt(v0);
        addRandomGradientAt(new Vector(v1.getX(), v0.getY()));
        addRandomGradientAt(new Vector(v0.getX(), v1.getY()));
        addRandomGradientAt(v1);

        Vector interpWeights = gridCoordinate.sub(v0);

        Double n0, n1, ix0, ix1, value;
        n0 = dotGridGradient(v0, gridCoordinate);
        n1 = dotGridGradient(new Vector(v1.getX(), v0.getY()), gridCoordinate);
        ix0 = lerp(n0, n1, interpWeights.getX());
        n0 = dotGridGradient(new Vector(v0.getX(), v1.getY()), gridCoordinate);
        n1 = dotGridGradient(v1, gridCoordinate);
        ix1 = lerp(n0, n1, interpWeights.getX());
        value = lerp(ix0, ix1, interpWeights.getY());

        return (value + 1) / 2;
    }

    private Double lerp(Double a0, Double a1, Double w) {
        return (1.0 - w) * a0 + w * a1;
    }

    private Double dotGridGradient(Vector gradientVector, Vector gridCoordinate) {
        Vector distanceVector = gridCoordinate.sub(gradientVector);
        Vector gradientAtCoord = gradient.get(gradientVector);
        return distanceVector.dotProduct(gradientAtCoord);
    }

    private void addRandomGradientAt(Vector coordinate) {
        if (gradient.get(coordinate) == null) {
            Double theta = rng.nextDouble();
            Double x = Math.cos(theta * 2.0 * Math.PI);
            Double y = Math.sin(theta * 2.0 * Math.PI);
            Vector randomVector = new Vector(x, y);
            gradient.put(coordinate, randomVector);
        }
    }
}
