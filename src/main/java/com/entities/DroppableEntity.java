package main.java.com.entities;

import main.java.com.util.Vector;

import java.util.Collections;

public abstract class DroppableEntity extends Entity {

    public DroppableEntity(Vector gridPosition) {
        super(gridPosition, Collections.emptyList(), Collections.emptyList());
    }

    public abstract void pickUp();

}
