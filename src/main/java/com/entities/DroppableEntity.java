package main.java.com.entities;

import main.java.com.util.Vector;

import java.util.ArrayList;

public abstract class DroppableEntity extends Entity {

    public DroppableEntity(Vector gridPosition) {
        super(gridPosition, new ArrayList<>());
    }

    public abstract void pickUp();

}
