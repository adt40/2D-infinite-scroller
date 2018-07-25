package main.java.com.entities;

import main.java.com.entities.alive.*;
import main.java.com.entities.droppable.DroppableEntity;
import main.java.com.entities.materials.MountainStoneBlockEntity;
import main.java.com.entities.materials.TreeEntity;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityManager {

    private static List<Class<? extends NonPlayerEntity>> spawnableEntities = Arrays.asList(
            WalkerEntity.class,
            ZombieEntity.class,
            FishEntity.class,
            TreeEntity.class,
            FlierEntity.class,
            MountainStoneBlockEntity.class);

    public static Player player;
    public static ArrayList<NonPlayerEntity> nonPlayerEntities;
    public static ArrayList<DroppableEntity> droppableEntities;

    public static void init() {
        player = new Player(new Vector(0, 0));
        nonPlayerEntities = new ArrayList<>();
        droppableEntities = new ArrayList<>();
    }

    public static void spawnEntities(TileType tileType, Vector gridCoordinate) {
        spawnableEntities.forEach(entityClass -> {
            try {
                Method shouldSpawn = entityClass.getDeclaredMethod("shouldSpawn", TileType.class, Vector.class);
                Boolean spawn = (Boolean) shouldSpawn.invoke(entityClass, tileType, gridCoordinate);
                if (spawn) {
                    nonPlayerEntities.add(entityClass.getDeclaredConstructor(Vector.class).newInstance(gridCoordinate));
                }
            } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException|InstantiationException e) {
                e.printStackTrace();
            }
        });
    }
}
