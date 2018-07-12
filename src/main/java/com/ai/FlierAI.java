package main.java.com.ai;

import main.java.com.entities.Entity;
import main.java.com.entities.Flier;
import main.java.com.entities.Tree;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.util.Vector;

import java.util.List;
import java.util.Random;
import java.util.TimerTask;

public class FlierAI extends TimerTask {

    private final Flier flier;
    private Vector currentDirection;
    private boolean sittingOnTree;

    public FlierAI(Flier flier) {
        this.flier = flier;
        currentDirection = new Vector(0, 0);
        sittingOnTree = false;
    }

    public void run() {
        Random random = new Random();
        // If you are not sitting on a tree, or you win the coin flip to leave the
        // tree you may or may not be sitting on, or there is no tree under you, move as normal.
        // Otherwise, do nothing. Just tweet a bit or something, idk what birds do.
        if (!sittingOnTree || random.nextDouble() <= 0.1 || !isTileTree(Terrain.grid.get(flier.getGridPosition()))) {
            sittingOnTree = false;
            continueMoving();
        }
    }

    private void continueMoving() {
        Random random = new Random();
        if (currentDirection.equals(new Vector(0, 0)) || random.nextDouble() <= 0.25) {
            currentDirection = Vector.getVectorFromDirectionInt(random.nextInt(4));
        }
        Vector nextPosition = flier.getGridPosition().add(currentDirection);
        Tile nextTile = Terrain.grid.get(nextPosition);
        if (isTileTree(nextTile) && !nextTile.isOccupiedBy(Flier.class)) {
            Terrain.grid.get(flier.getGridPosition()).removeOccupier(flier);
            Terrain.grid.get(nextPosition).addOccupier(flier);
            flier.setGridPosition(nextPosition);
            sittingOnTree = true;
        } else {
            flier.move(currentDirection);
        }
    }

    private boolean isTileTree(Tile tile) {
        return tile != null && tile.getOccupyingEntities() != null && tile.getOccupyingEntities().stream().anyMatch(entity -> entity instanceof Tree);
    }
}
