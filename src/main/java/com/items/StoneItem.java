package main.java.com.items;

import main.java.com.entities.EntityManager;
import main.java.com.entities.StoneBlockEntity;
import main.java.com.terrain.Terrain;
import main.java.com.util.Vector;

public class StoneItem extends PlaceableItem {

    public StoneItem() {
        super("Stone");
    }

    public void placeAt(Vector gridCoordinate) {
        Double distance = EntityManager.player.getGridPosition().distanceTo(gridCoordinate);
        if (distance <= maxPlaceDistance) {
            StoneBlockEntity stoneBlockEntity = new StoneBlockEntity(gridCoordinate);
            EntityManager.nonPlayerEntities.add(stoneBlockEntity);
            Terrain.grid.get(gridCoordinate).addOccupier(stoneBlockEntity);
            EntityManager.player.getSelectedItem().addAmount(-1);
        }
    }
}
