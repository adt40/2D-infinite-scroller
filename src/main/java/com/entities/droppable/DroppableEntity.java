package main.java.com.entities.droppable;

import main.java.com.entities.Entity;
import main.java.com.util.Vector;

import java.util.Collections;

public abstract class DroppableEntity extends Entity {

    public DroppableEntity(Vector gridPosition) {
        super(gridPosition, Collections.emptyList(), Collections.emptyList(), 1);
    }

    public abstract void pickUp();

}
