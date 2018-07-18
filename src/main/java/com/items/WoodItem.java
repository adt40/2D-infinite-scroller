package main.java.com.items;

import main.java.com.entities.EntityManager;
import main.java.com.entities.materials.WoodBlockEntity;
import main.java.com.terrain.Terrain;
import main.java.com.util.Vector;

public class WoodItem extends PlaceableItem {

    public WoodItem() {
        super("Wood");
    }

    public void placeAt(Vector gridCoordinate) {
        Double distance = EntityManager.player.getGridPosition().distanceTo(gridCoordinate);
        if (distance <= maxPlaceDistance) {
            WoodBlockEntity woodBlockEntity = new WoodBlockEntity(gridCoordinate);
            EntityManager.nonPlayerEntities.add(woodBlockEntity);
            Terrain.grid.get(gridCoordinate).addOccupier(woodBlockEntity);
            EntityManager.player.getSelectedItem().addAmount(-1);
        }
    }
}
