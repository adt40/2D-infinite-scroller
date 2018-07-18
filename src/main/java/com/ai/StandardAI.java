package main.java.com.ai;

import main.java.com.entities.Entity;
import main.java.com.entities.EntityManager;
import main.java.com.entities.NonPlayerEntity;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.util.Vector;

import java.util.Random;

public class StandardAI {
    static void wander(NonPlayerEntity entity) {
        boolean trapped = entity.isTrapped();
        if (!trapped) {

            Random random = new Random();
            boolean isWalkable = false;
            Tile nextTile = null;
            Vector direction = new Vector(0, 0);

            while (!isWalkable) {
                int directionValue = random.nextInt(4);
                direction = Vector.getVectorFromDirectionInt(directionValue);
                Vector nextPosition = entity.getGridPosition().add(direction);
                nextTile = Terrain.grid.get(nextPosition);
                if (nextTile != null) {
                    isWalkable = entity.isTileWalkable(nextTile);
                }
            }

            if (entity.isTileWalkable(nextTile)) {
                entity.move(direction);
            }
        }
    }

    static void moveTo(NonPlayerEntity entity, Vector goal) {
        boolean trapped = entity.isTrapped();
        if (!trapped) {
            Double minDistanceToGoal = Double.MAX_VALUE;
            Vector minDirection = new Vector(0, 0);
            for (int i = 0; i < 4; i++) {
                Vector direction = Vector.getVectorFromDirectionInt(i);
                Vector possibleNewPosition = entity.getGridPosition().add(direction);
                if (entity.isTileWalkable(Terrain.grid.get(possibleNewPosition))) {
                    Double distanceToGoal = possibleNewPosition.distanceTo(goal);
                    if (distanceToGoal < minDistanceToGoal) {
                        minDistanceToGoal = distanceToGoal;
                        minDirection = direction;
                    }
                }
            }
            entity.move(minDirection);
        }
    }

    static void attack(NonPlayerEntity entity, Entity target) {
        //TODO: Health and damage
    }
}