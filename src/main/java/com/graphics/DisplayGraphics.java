package main.java.com.graphics;

import main.java.com.entities.*;
import main.java.com.terrain.*;
import main.java.com.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

public class DisplayGraphics extends JPanel {

    private int gridSize;
    private final int numOctaves;
    private final Double frequency;
    private final INoiseGenerator noiseGenerator;

    public DisplayGraphics(int gridSize, int numOctaves, Double frequency) {
        this.gridSize = gridSize;
        this.numOctaves = numOctaves;
        this.frequency = frequency;

        noiseGenerator = new SimplexNoise();

        UserInputManager userInputManager = new UserInputManager(this);
        addMouseListener(userInputManager);
        addMouseWheelListener(userInputManager);

        EntityManager.init();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (EntityManager.player.hasChanged() || EntityManager.nonPlayerEntities.stream().anyMatch(NonPlayerEntity::hasChanged)) {
                    repaint();
                }
            }
        }, 50, 50);
    }

    void scaleGridSize(Double amount) {
        gridSize *= amount;
    }

    int getGridSize() {
        return gridSize;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Vector offsetFromOrigin = EntityManager.player.getGridPosition().multiplyByScalar(gridSize);
        Vector offsetFromGrid = new Vector(offsetFromOrigin.getX() % gridSize, offsetFromOrigin.getY() % gridSize);

        int halfGridWidth = (int) (getWidth() / gridSize / 2.0) + 1;
        int halfGridHeight = (int) (getHeight() / gridSize / 2.0) + 1;

        drawGrid(g2, offsetFromOrigin, offsetFromGrid, halfGridWidth, halfGridHeight);
        drawNonPlayerEntities(g2, offsetFromOrigin, offsetFromGrid, halfGridWidth, halfGridHeight);
        drawDroppableEntities(g2, offsetFromOrigin, offsetFromGrid, halfGridWidth, halfGridHeight);
        EntityManager.player.paint(g2, halfGridWidth * gridSize, halfGridHeight * gridSize, gridSize);
        drawItemBar(g2);
    }

    private void drawGrid(Graphics2D g, Vector offsetFromOrigin, Vector offsetFromGrid, int halfGridWidth, int halfGridHeight) {
        applyToGrid(g, offsetFromOrigin, offsetFromGrid, halfGridWidth, halfGridHeight, (graphics, xPos, yPos, gridCoordinate) -> {
            addValueToGrid(gridCoordinate);
            Tile tile = Terrain.grid.get(gridCoordinate);
            Color color = tile.getTileType().getColor();
            graphics.setColor(color);
            graphics.fillRect(xPos, yPos, gridSize, gridSize);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(xPos, yPos, gridSize, gridSize);
        });
    }

    private void drawNonPlayerEntities(Graphics2D g, Vector offsetFromOrigin, Vector offsetFromGrid, int halfGridWidth, int halfGridHeight) {
        applyToGrid(g, offsetFromOrigin, offsetFromGrid, halfGridWidth, halfGridHeight, (graphics, xPos, yPos, gridCoordinate) -> EntityManager.nonPlayerEntities.forEach(entity -> {
            if (entity.getGridPosition().equals(gridCoordinate)) {
                entity.paint(graphics, xPos, yPos, gridSize);
            }
        }));
    }

    private void drawDroppableEntities(Graphics2D g, Vector offsetFromOrigin, Vector offsetFromGrid, int halfGridWidth, int halfGridHeight) {
        applyToGrid(g, offsetFromOrigin, offsetFromGrid, halfGridWidth, halfGridHeight, (graphics, xPos, yPos, gridCoordinate) -> EntityManager.droppableEntities.forEach(entity -> {
            if (entity.getGridPosition().equals(gridCoordinate)) {
                entity.paint(graphics, xPos, yPos, gridSize);
            }
        }));
    }

    @FunctionalInterface
    interface GridFunction<graphics, xPos, yPos, gridCoordinate> {
        void apply(graphics g, xPos x, yPos y, gridCoordinate c);
    }

    private void applyToGrid(Graphics2D g, Vector offsetFromOrigin, Vector offsetFromGrid, int halfGridWidth, int halfGridHeight, GridFunction<Graphics2D, Integer, Integer, Vector> function) {
        Integer leftmostGridValue = (int) (offsetFromOrigin.getX() / gridSize) - halfGridWidth - 1;
        Integer rightmostGridValue = (int) (offsetFromOrigin.getX() / gridSize) + halfGridWidth + 1;
        Integer topmostGridValue = (int) (offsetFromOrigin.getY() / gridSize) - halfGridHeight - 1;
        Integer botmostGridValue = (int) (offsetFromOrigin.getY() / gridSize) + halfGridHeight + 1;

        for (int x = leftmostGridValue; x < rightmostGridValue; x++) {
            for (int y = topmostGridValue; y < botmostGridValue; y++) {
                Vector gridCoordinate = new Vector(x, y);
                int xPos = (x - leftmostGridValue - 1) * gridSize - offsetFromGrid.getX().intValue();
                int yPos = (y - topmostGridValue - 1) * gridSize - offsetFromGrid.getY().intValue();
                function.apply(g, xPos, yPos, gridCoordinate);
            }
        }
    }

    private void drawItemBar(Graphics2D g) {
        int squareLength = getWidth() / 12;

        int startX = squareLength;
        int width = getWidth() - squareLength * 2;

        int startY = (int)(getHeight() - squareLength * 1.6);
        int height = squareLength;

        g.setColor(new Color(181, 171, 140, 200));
        g.fillRect(startX, startY, width, height);

        g.setColor(new Color(0, 0, 0, 200));
        for (int i = 0; i < width; i++) {
            int x = i * squareLength + startX;

            if (i < EntityManager.player.getItems().size()) {
                String name = EntityManager.player.getItems().get(i).getName();
                int count = EntityManager.player.getItems().get(i).getAmount();
                g.drawString(name, x + 5, startY + squareLength / 2);
                if (count > 1) {
                    g.drawString(Integer.toString(count), x + 5, startY + 3 * squareLength / 4);
                }

            }

            if (EntityManager.player.getSelectedItemIndex() == i) {
                continue;
            }

            g.drawRect(x, startY, squareLength, height);
        }

        g.setColor(new Color(173, 4, 0, 200));
        g.setStroke(new BasicStroke(3));
        int x = EntityManager.player.getSelectedItemIndex() * squareLength + startX;
        g.drawRect(x, startY, squareLength, height);
    }

    private void addValueToGrid(Vector gridCoordinate) {
        if (!Terrain.grid.containsKey(gridCoordinate)) {
            Double noise = 0.0;
            Double scaling = 1.0;
            Double postScaling = 0.0;
            for (int i = 0; i < numOctaves; i++) {
                Double currentFrequency = Math.pow(2, i) * frequency;
                Vector coordinate = new Vector(gridCoordinate.getX() * currentFrequency, gridCoordinate.getY() * currentFrequency);
                noise += noiseGenerator.noiseAt(coordinate) * scaling;
                postScaling += scaling;
                scaling /= 2;
            }
            Double noiseValue = noise / postScaling;
            Tile tile = new Tile(noiseValue);
            Terrain.grid.put(gridCoordinate, tile);
            EntityManager.spawnEntities(tile.getTileType(), gridCoordinate);
        }
    }
}
