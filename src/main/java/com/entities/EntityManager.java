package main.java.com.entities;

import main.java.com.entities.alive.Player;
import main.java.com.entities.droppable.DroppableEntity;
import main.java.com.util.Vector;

import java.util.ArrayList;

public class EntityManager {
    public static Player player;
    public static ArrayList<NonPlayerEntity> nonPlayerEntities;
    public static ArrayList<DroppableEntity> droppableEntities;

    public static void init() {
        player = new Player(new Vector(0, 0));
        nonPlayerEntities = new ArrayList<>();
        droppableEntities = new ArrayList<>();
    }
}
