package main.java.com.ai;

import main.java.com.entities.EntityManager;
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
     * If the zombie is ever directly next to a target, it will attack it
     */
    public void run() {
        //update state
        updateState();

        //select action based on the state
        performStatefulAction();
    }

    private void performStatefulAction() {
        if (state == 0) {
            StandardAI.wander(zombieEntity);
        } else if (state == 1) {
            StandardAI.moveTo(targetPosition, zombieEntity);
            if (zombieEntity.getGridPosition().equals(targetPosition)) {
                state = 0;
                zombieEntity.setIsTargeting(false);
                targetPosition = null;
            }
        } else if (state == 2) {
            StandardAI.attack(zombieEntity, EntityManager.player, 10);
        }
    }

    private void updateState() {
        Double distanceToPlayer = EntityManager.player.getGridPosition().distanceTo(zombieEntity.getGridPosition());
        if (distanceToPlayer <= 1) {
            state = 2;
            zombieEntity.setIsTargeting(true);
        } else if (state == 2) {
            state = 0;
            zombieEntity.setIsTargeting(false);
        } else if (distanceToPlayer <= 6) {
            boolean intersects = StandardAI.isLineOfSight(zombieEntity, EntityManager.player, distanceToPlayer);

            //if nothing is blocking the view, set the target position to the player's current position and change state
            if (!intersects) {
                targetPosition = EntityManager.player.getGridPosition();
                state = 1;
                zombieEntity.setIsTargeting(true);
            }
        } else if (targetPosition == null) {
            state = 0;
        }
    }
}
