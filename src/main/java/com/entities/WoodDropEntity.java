package main.java.com.entities;

import main.java.com.items.WoodItem;
import main.java.com.util.Vector;

import java.awt.*;


public class WoodDropEntity extends DroppableEntity {

    public WoodDropEntity(Vector gridCoordinate) {
        super(gridCoordinate);
    }

    @Override
    public void paint(Graphics g, int xPos, int yPos, int gridSize) {
        g.setColor(new Color(98, 66, 0));
        int size = (int)(gridSize * 0.5);
        int offset = (gridSize - size) / 2;
        g.fillRect(xPos + offset, yPos + offset, size, size);
    }


    @Override
    public void pickUp() {
        EntityManager.player.addItem(new WoodItem());
        EntityManager.droppableEntities.remove(this);
    }
}
