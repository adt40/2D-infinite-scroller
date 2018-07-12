package main.java.com.entities;

import main.java.com.items.InventoryItem;
import main.java.com.items.tools.*;
import main.java.com.terrain.TileType;
import main.java.com.util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    InventoryItem getSelectedItem() {
        return items.get(selectedItemIndex);
    }

    public void setSelectedItemIndex(int index) {
        if (index >= 0 && index < items.size()) {
            selectedItemIndex = index;
        }
    }

    public void incrementSelectedItemIndexBy(int amount) {
        if (selectedItemIndex + amount < 0) {
            selectedItemIndex = items.size() + selectedItemIndex + amount;
        } else {
            selectedItemIndex = (selectedItemIndex + amount) % items.size();
        }
        System.out.println(items.get(selectedItemIndex).getName());
    }

    @Override
    public void paint(Graphics g, int xPos, int yPos, int gridSize) {
        g.setColor(Color.RED);
        g.fillOval(xPos, yPos, gridSize, gridSize);
    }

}
