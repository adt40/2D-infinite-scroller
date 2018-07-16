package main.java.com.items;

import main.java.com.util.Vector;

public abstract class PlaceableItem extends InventoryItem {

    final Double maxPlaceDistance = 4.0;

    public PlaceableItem(String name) {
        super(name);
    }

    public abstract void placeAt(Vector gridCoordinate);
}
