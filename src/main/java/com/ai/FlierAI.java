package main.java.com.ai;

import main.java.com.entities.Flier;
import main.java.com.util.Vector;

import java.util.Random;
import java.util.TimerTask;

public class FlierAI extends TimerTask {

    private final Flier flier;
    private Vector currentDirection;

    public FlierAI(Flier flier) {
        this.flier = flier;
        currentDirection = new Vector(0, 0);
    }

    public void run() {
        Random random = new Random();
        if (currentDirection.equals(new Vector(0, 0)) || random.nextDouble() <= 0.25) {
            currentDirection = Vector.getVectorFromDirectionInt(random.nextInt(4));
        }
        flier.move(currentDirection);
    }
}
