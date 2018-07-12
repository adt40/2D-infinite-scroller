package main.java.com.ai;

import main.java.com.entities.Walker;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.util.Vector;

import java.util.Random;
import java.util.TimerTask;

public class WalkerAI extends TimerTask {

    private final Walker walker;

    public WalkerAI(Walker walker) {
        this.walker = walker;
    }

    public void run() {
        boolean trapped = walker.isTrapped();
        if (!trapped) {

            Random random = new Random();
            boolean isWalkable = false;
            Tile nextTile = null;
            Vector direction = new Vector(0, 0);

            while (!isWalkable) {
                int directionValue = random.nextInt(4);
                direction = Vector.getVectorFromDirectionInt(directionValue);
                Vector nextPosition = walker.getGridPosition().add(direction);
                nextTile = Terrain.grid.get(nextPosition);
                if (nextTile != null) {
                    isWalkable = walker.isTileWalkable(nextTile);
                }
            }

            if (walker.isTileWalkable(nextTile)) {
                walker.move(direction);
            }
        }
    }

}
