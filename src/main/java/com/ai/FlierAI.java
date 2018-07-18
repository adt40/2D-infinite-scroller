package main.java.com.ai;

import main.java.com.entities.FlierEntity;
import main.java.com.entities.TreeEntity;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.util.Vector;

import java.util.Random;
import java.util.TimerTask;

public class FlierAI extends TimerTask {

    private final FlierEntity flierEntity;
    private Vector currentDirection;
    private boolean sittingOnTree;

    public FlierAI(FlierEntity flierEntity) {
        this.flierEntity = flierEntity;
        currentDirection = new Vector(0, 0);
        sittingOnTree = false;
    }

    public void run() {
        Random random = new Random();
        // If you are not sitting on a tree, or you win the coin flip to leave the
        // tree you may or may not be sitting on, or there is no tree under you, move as normal.
        // Otherwise, do nothing. Just tweet a bit or something, idk what birds do.
        if (!sittingOnTree || random.nextDouble() <= 0.1 || !isTileTree(Terrain.grid.get(flierEntity.getGridPosition()))) {
            sittingOnTree = false;
            continueMoving();
        }
    }

    private void continueMoving() {
        Random random = new Random();
        if (currentDirection.equals(new Vector(0, 0)) || random.nextDouble() <= 0.25) {
            currentDirection = Vector.getVectorFromDirectionInt(random.nextInt(4));
        }
        Vector nextPosition = flierEntity.getGridPosition().add(currentDirection);
        Tile nextTile = Terrain.grid.get(nextPosition);
        if (isTileTree(nextTile) && !nextTile.isOccupiedBy(FlierEntity.class)) {
            Terrain.grid.get(flierEntity.getGridPosition()).removeOccupier(flierEntity);
            Terrain.grid.get(nextPosition).addOccupier(flierEntity);
            flierEntity.setGridPosition(nextPosition);
            sittingOnTree = true;
        } else {
            flierEntity.move(currentDirection);
        }
    }

    private boolean isTileTree(Tile tile) {
        return tile != null && tile.getOccupyingEntities() != null && tile.getOccupyingEntities().stream().anyMatch(entity -> entity instanceof TreeEntity);
    }
}
