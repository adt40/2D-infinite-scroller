package main.java.com.graphics;

import main.java.com.entities.*;
import main.java.com.terrain.*;
import main.java.com.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DisplayGraphics extends JPanel implements MouseListener, MouseWheelListener {

    private final int gridSize;
    private final int numOctaves;
    private final Double frequency;
    private final INoiseGenerator noiseGenerator;

    public DisplayGraphics() {
        numOctaves = 3;
        frequency = 0.1;
        gridSize = 24;
        noiseGenerator = new SimplexNoise();
        InstantiatedEntities.nonPlayerEntities = new ArrayList<>();
        InstantiatedEntities.player = new Player();
        setKeyBindings();
        addMouseListener(this);
        addMouseWheelListener(this);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (InstantiatedEntities.player.hasChanged() || InstantiatedEntities.nonPlayerEntities.stream().anyMatch(NonPlayerEntity::hasChanged)) {
                    repaint();
                }
            }
        }, 50, 50);
    }

    @Override
    public void paint(Graphics g) {
        Vector offsetFromOrigin = InstantiatedEntities.player.getGridPosition().multiplyByScalar(gridSize);
        Vector offsetFromGrid = new Vector(offsetFromOrigin.getX() % gridSize, offsetFromOrigin.getY() % gridSize);

        int halfGridWidth = (int) (getWidth() / gridSize / 2.0) + 1;
        int halfGridHeight = (int) (getHeight() / gridSize / 2.0) + 1;

        drawGrid(g, offsetFromOrigin, offsetFromGrid, halfGridWidth, halfGridHeight);
        InstantiatedEntities.player.paint(g, halfGridWidth * gridSize, halfGridHeight * gridSize, gridSize);
        drawNonPlayerEntities(g, offsetFromOrigin, offsetFromGrid, halfGridWidth, halfGridHeight);
    }

    private void drawGrid(Graphics g, Vector offsetFromOrigin, Vector offsetFromGrid, int halfGridWidth, int halfGridHeight) {
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

    private void drawNonPlayerEntities(Graphics g, Vector offsetFromOrigin, Vector offsetFromGrid, int halfGridWidth, int halfGridHeight) {
        applyToGrid(g, offsetFromOrigin, offsetFromGrid, halfGridWidth, halfGridHeight, (graphics, xPos, yPos, gridCoordinate) -> InstantiatedEntities.nonPlayerEntities.forEach(entity -> {
            if (entity.getGridPosition().equals(gridCoordinate)) {
                entity.paint(graphics, xPos, yPos, gridSize);
            }
        }));
    }

    @FunctionalInterface
    interface GridFunction<graphics, xPos, yPos, gridCoordinate> {
        void apply(graphics g, xPos x, yPos y, gridCoordinate c);
    }

    private void applyToGrid(Graphics g, Vector offsetFromOrigin, Vector offsetFromGrid, int halfGridWidth, int halfGridHeight, GridFunction<Graphics, Integer, Integer, Vector> function) {
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
            spawnEntities(tile.getTileType(), gridCoordinate);
        }
    }

    private void spawnEntities(TileType tileType, Vector gridCoordinate) {
        if (NonPlayerEntity.shouldSpawn(tileType, Walker.class)) {
            InstantiatedEntities.nonPlayerEntities.add(new Walker(gridCoordinate));
        } else if (NonPlayerEntity.shouldSpawn(tileType, Flier.class)) {
            InstantiatedEntities.nonPlayerEntities.add(new Flier(gridCoordinate));
        } else if (NonPlayerEntity.shouldSpawn(tileType, Tree.class)) {
            InstantiatedEntities.nonPlayerEntities.add(new Tree(gridCoordinate));
        }
    }


    //TODO: Abstract out user inputs, this class is messy enough as is
    private void setKeyBindings() {
        ActionMap actionMap = getActionMap();
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        String w = "0";
        String s = "1";
        String a = "2";
        String d = "3";


        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), w);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), s);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), a);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), d);

        actionMap.put(w, new KeyAction(w));
        actionMap.put(s, new KeyAction(s));
        actionMap.put(a, new KeyAction(a));
        actionMap.put(d, new KeyAction(d));
    }

    private class KeyAction extends AbstractAction {
        KeyAction(String actionCommand) {
            putValue(ACTION_COMMAND_KEY, actionCommand);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvt) {
            InstantiatedEntities.player.move(Vector.getVectorFromDirectionInt(Integer.parseInt(actionEvt.getActionCommand())));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Vector offsetFromOrigin = InstantiatedEntities.player.getGridPosition().multiplyByScalar(gridSize);

        int halfGridWidth = (int) (getWidth() / gridSize / 2.0) + 1;
        int halfGridHeight = (int) (getHeight() / gridSize / 2.0) + 1;

        int x = (int)(offsetFromOrigin.getX() / gridSize) - halfGridWidth + (e.getX() / gridSize);
        int y = (int) (offsetFromOrigin.getY() / gridSize) - halfGridHeight + (e.getY() / gridSize);

        Vector selectedLocation = new Vector(x, y);
        Tile tile = Terrain.grid.get(selectedLocation);
        if (tile.isOccupied()) {
            Entity selectedEntity = tile.getOccupyingEntity();
            if (selectedEntity instanceof NonPlayerEntity) {
                ((NonPlayerEntity) selectedEntity).click();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        InstantiatedEntities.player.incrementSelectedItemIndexBy(e.getWheelRotation());
    }
}
