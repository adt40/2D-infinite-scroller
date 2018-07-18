package main.java.com.ai;

import main.java.com.entities.EntityManager;
import main.java.com.entities.NonPlayerEntity;
import main.java.com.entities.alive.ZombieEntity;
import main.java.com.util.Vector;

import java.util.TimerTask;


public class ZombieAI extends TimerTask {

    private final ZombieEntity zombieEntity;

    private Vector targetPosition;
    private int state;


    public ZombieAI(ZombieEntity zombieEntity) {
        this.zombieEntity = zombieEntity;
        state = 0;
    }

    /**
     * Zombies will wander aimlessly until they see the player by line of sight
     * Then, they will move to the tile where they last saw the player (this updates if the player is still in view)
     * If the zombie reaches the tile and doesn't see the player, it will go back to wandering
     */
    public void run() {

        //update state
        Double distanceToPlayer = EntityManager.player.getGridPosition().distanceTo(zombieEntity.getGridPosition());
        if (distanceToPlayer <= 1) {
            state = 2;
        } else {
            checkLineOfSight(distanceToPlayer);
        }

        //select action based on the state
        if (state == 0) {
            StandardAI.wander(zombieEntity);
        } else if (state == 1) {
            StandardAI.moveTo(zombieEntity, targetPosition);
            if (zombieEntity.getGridPosition().equals(targetPosition)) {
                state = 0;
                zombieEntity.setIsTargetting(false);
            }
        } else if (state == 2) {
            StandardAI.attack(zombieEntity, EntityManager.player);
        }
    }

    private void checkLineOfSight(Double distanceToPlayer) {
        //Check distance
        if (distanceToPlayer <= 6) {
            boolean intersects = false;
            Vector directionToPlayer = EntityManager.player.getGridPosition().sub(zombieEntity.getGridPosition());

            //Check for anything blocking the zombie's view of you
            for (int i = 0; i < EntityManager.nonPlayerEntities.size(); i++) {
                NonPlayerEntity entity = EntityManager.nonPlayerEntities.get(i);
                if (!(entity instanceof ZombieEntity)) {
                    if (intersection(entity.getGridPosition(), zombieEntity.getGridPosition(), directionToPlayer)) {
                        intersects = true;
                        break;
                    }
                }
            }

            //if nothing is blocking the view, set the target position to the player's current position and change state
            if (!intersects) {
                targetPosition = EntityManager.player.getGridPosition();
                state = 1;
                zombieEntity.setIsTargetting(true);
            }
        }
    }

    private boolean intersection(Vector objectOrigin, Vector rayOrigin, Vector rayDirection) {
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

    private boolean inside(Double val) {
        return 0 <= val && val <= 1;
    }

    private Double getT(Vector p, Vector pd, Vector o, Vector od) {
        return ((p.getY() - o.getY()) * od.getX() - (p.getX() - o.getX()) * od.getY()) / (pd.getX() * od.getY() - pd.getY() * od.getX());
    }

    private Double getU(Vector p, Vector pd, Vector o, Vector od) {
        return ((o.getY() - p.getY()) * pd.getX() - (o.getX() - p.getX()) * pd.getY()) / (od.getX() * pd.getY() - od.getY() * pd.getX());
    }
}
