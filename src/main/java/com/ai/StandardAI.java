package main.java.com.ai;

import main.java.com.entities.Entity;
import main.java.com.entities.EntityManager;
import main.java.com.entities.NonPlayerEntity;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.util.Vector;

import java.util.ArrayList;
import java.util.Random;

class StandardAI {
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

    static void moveTo(Vector goal, NonPlayerEntity entity) {
        boolean trapped = entity.isTrapped();
        if (!trapped) {
            Double minDistanceToGoal = Double.MAX_VALUE;
            ArrayList<Vector> minDirections = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Vector direction = Vector.getVectorFromDirectionInt(i);
                Vector possibleNewPosition = entity.getGridPosition().add(direction);
                if (entity.isTileWalkable(Terrain.grid.get(possibleNewPosition))) {
                    Double distanceToGoal = possibleNewPosition.distanceTo(goal);
                    if (distanceToGoal < minDistanceToGoal) {
                        minDistanceToGoal = distanceToGoal;
                        minDirections.clear();
                        minDirections.add(direction);
                    } else if (distanceToGoal.equals(minDistanceToGoal)) {
                        minDirections.add(direction);
                    }
                }
            }
            entity.move(minDirections.get(new Random().nextInt(minDirections.size())));
        }
    }

    static void attack(NonPlayerEntity entity, Entity target, Integer damage) {
        //System.out.println(entity.getClass().toString() + " attacked " + target.getClass().toString());
        target.dealDamage(damage);
    }


    static boolean isLineOfSight(Entity self, Entity target, Double distanceToEntity) {
        boolean intersects = false;
        Vector directionToEntity = target.getGridPosition().sub(self.getGridPosition());

        //Check for anything blocking the zombie's view of you
        for (int i = 0; i < EntityManager.nonPlayerEntities.size(); i++) {
            NonPlayerEntity nonPlayerEntity = EntityManager.nonPlayerEntities.get(i);

            if (!self.getClass().isInstance(nonPlayerEntity)) {
                if (intersection(nonPlayerEntity.getGridPosition(), self.getGridPosition(), directionToEntity)) {
                    intersects = true;
                    break;
                }
            }
        }

        return intersects;
    }

    private static boolean intersection(Vector objectOrigin, Vector rayOrigin, Vector rayDirection) {
        Vector p0 = objectOrigin.add(new Vector(-0.5, -0.5));
        Vector p1 = objectOrigin.add(new Vector(0.5, 0.5));

        Vector p0dx = new Vector(1, 0);
        Vector p0dy = new Vector(0, 1);
        Vector p1dx = new Vector(-1, 0);
        Vector p1dy = new Vector(0, -1);

        Double t0x = getT(p0, p0dx, rayOrigin, rayDirection);
        Double u0x = getU(p0, p0dx, rayOrigin, rayDirection);
        if (inside(t0x) && inside(u0x)) {
            return true;
        }

        Double t0y = getT(p0, p0dy, rayOrigin, rayDirection);
        Double u0y = getU(p0, p0dy, rayOrigin, rayDirection);
        if (inside(t0y) && inside(u0y)) {
            return true;
        }

        Double t1x = getT(p1, p1dx, rayOrigin, rayDirection);
        Double u1x = getU(p1, p1dx, rayOrigin, rayDirection);
        if (inside(t1x) && inside(u1x)) {
            return true;
        }

        Double t1y = getT(p1, p1dy, rayOrigin, rayDirection);
        Double u1y = getU(p1, p1dy, rayOrigin, rayDirection);
        if (inside(t1y) && inside(u1y)) {
            return true;
        }

        return false;
    }

    private static boolean inside(Double val) {
        return 0 <= val && val <= 1;
    }

    private static Double getT(Vector p, Vector pd, Vector o, Vector od) {
        return ((p.getY() - o.getY()) * od.getX() - (p.getX() - o.getX()) * od.getY()) / (pd.getX() * od.getY() - pd.getY() * od.getX());
    }

    private static Double getU(Vector p, Vector pd, Vector o, Vector od) {
        return ((o.getY() - p.getY()) * pd.getX() - (o.getX() - p.getX()) * pd.getY()) / (od.getX() * pd.getY() - od.getY() * pd.getX());
    }
}
