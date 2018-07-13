package main.java.com.entities;

import main.java.com.items.InventoryItem;
import main.java.com.items.tools.*;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Player extends Entity {

    static final List<TileType> TILE_TYPES = Arrays.asList(TileType.GRASS1, TileType.GRASS2, TileType.GRASS3, TileType.FOREST);

    private ArrayList<InventoryItem> items;
    private int selectedItemIndex;

    public Player() {
        super(new Vector(0, 0), TILE_TYPES);
        selectedItemIndex = 0;
        items = new ArrayList<>();
        items.add(new Axe());
        items.add(new Sword());
        items.add(new Bow(5));
    }

    public ArrayList<InventoryItem> getItems() {
        return items;
    }

    public void addItem(InventoryItem item) {
        Optional<InventoryItem> optionalItem = items.stream().filter(invItem -> invItem.getName().equals(item.getName())).findAny();
        if (optionalItem.isPresent()) {
            items.get(items.indexOf(optionalItem.get())).addAmount(1);
        } else {
            items.add(item);
        }
    }

    InventoryItem getSelectedItem() {
        return items.get(selectedItemIndex);
    }

    @Override
    public void move(Vector direction) {
        Vector nextPosition = getGridPosition().add(direction);
        Tile nextTile = Terrain.grid.get(nextPosition);
        if (isTileWalkable(nextTile)) {
            Tile tile = Terrain.grid.get(getGridPosition());
            tile.removeOccupier(this);
            nextTile.addOccupier(this);
            setGridPosition(nextPosition);
            setMoved(true);
            checkForDroppableEntities();
        }
    }

    private void checkForDroppableEntities() {
        List<DroppableEntity> droppableEntities = EntityManager.droppableEntities;
        int i = 0;
        while (i < droppableEntities.size()) {
            if (droppableEntities.get(i).getGridPosition().equals(getGridPosition())) {
                droppableEntities.get(i).pickUp();
            } else {
                i++;
            }
        }
    }


    public void setSelectedItemIndex(int index) {
        if (index >= 0 && index < items.size()) {
            selectedItemIndex = index;
            System.out.println(items.get(selectedItemIndex).getName() + " " + items.get(selectedItemIndex).getAmount());
        }
    }

    public void incrementSelectedItemIndexBy(int amount) {
        if (selectedItemIndex + amount < 0) {
            selectedItemIndex = items.size() + selectedItemIndex + amount;
        } else {
            selectedItemIndex = (selectedItemIndex + amount) % items.size();
        }
        System.out.println(items.get(selectedItemIndex).getName() + " " + items.get(selectedItemIndex).getAmount());
    }

    @Override
    public void paint(Graphics g, int xPos, int yPos, int gridSize) {
        g.setColor(Color.RED);
        g.fillOval(xPos, yPos, gridSize, gridSize);
    }

}
