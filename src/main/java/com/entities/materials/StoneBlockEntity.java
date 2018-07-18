package main.java.com.entities.materials;

import main.java.com.util.Vector;

import java.awt.*;
import java.util.Collections;

public class StoneBlockEntity extends StoneEntity {

    public StoneBlockEntity(Vector position) {
        super(position, Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public void paint(Graphics g, int xPos, int yPos, int gridSize) {
        g.setColor(new Color(100, 112, 119));
        g.fillRect(xPos + 1, yPos + 1, gridSize - 2, gridSize - 2);
    }

}
