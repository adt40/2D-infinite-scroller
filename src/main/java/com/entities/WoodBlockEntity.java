package main.java.com.entities;

import main.java.com.util.Vector;

import java.awt.*;
import java.util.Collections;

public class WoodBlockEntity extends WoodenEntity {

    public WoodBlockEntity(Vector position) {
        super(position, Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public void paint(Graphics g, int xPos, int yPos, int gridSize) {
        g.setColor(new Color(133, 98, 63));
        g.fillRect(xPos + 1, yPos + 1, gridSize - 2, gridSize - 2);
    }
}
