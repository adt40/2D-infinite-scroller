package main.java.com.graphics;

import main.java.com.entities.Entity;
import main.java.com.entities.EntityManager;
import main.java.com.entities.NonPlayerEntity;
import main.java.com.items.PlaceableItem;
import main.java.com.terrain.Terrain;
import main.java.com.terrain.Tile;
import main.java.com.util.Vector;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

class UserInputManager implements MouseListener, MouseWheelListener {

    private DisplayGraphics target;

    UserInputManager(DisplayGraphics target) {
        this.target = target;
        setKeyBindings();
    }

    private void setKeyBindings() {
        ActionMap actionMap = target.getActionMap();
        InputMap inputMap = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        String w = "0";
        String s = "1";
        String a = "2";
        String d = "3";

        String plus = "+";
        String minus = "-";

        String inv0 = "inv0";
        String inv1 = "inv1";
        String inv2 = "inv2";
        String inv3 = "inv3";
        String inv4 = "inv4";
        String inv5 = "inv5";
        String inv6 = "inv6";
        String inv7 = "inv7";
        String inv8 = "inv8";
        String inv9 = "inv9";

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), w);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), s);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), a);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), d);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), plus);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), minus);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), inv0);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), inv1);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), inv2);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0), inv3);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0), inv4);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0), inv5);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0), inv6);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0), inv7);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_9, 0), inv8);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0), inv9);

        actionMap.put(w, new UserInputManager.KeyAction(w));
        actionMap.put(s, new UserInputManager.KeyAction(s));
        actionMap.put(a, new UserInputManager.KeyAction(a));
        actionMap.put(d, new UserInputManager.KeyAction(d));

        actionMap.put(plus, new UserInputManager.KeyAction(plus));
        actionMap.put(minus, new UserInputManager.KeyAction(minus));

        actionMap.put(inv0, new UserInputManager.KeyAction(inv0));
        actionMap.put(inv1, new UserInputManager.KeyAction(inv1));
        actionMap.put(inv2, new UserInputManager.KeyAction(inv2));
        actionMap.put(inv3, new UserInputManager.KeyAction(inv3));
        actionMap.put(inv4, new UserInputManager.KeyAction(inv4));
        actionMap.put(inv5, new UserInputManager.KeyAction(inv5));
        actionMap.put(inv6, new UserInputManager.KeyAction(inv6));
        actionMap.put(inv7, new UserInputManager.KeyAction(inv7));
        actionMap.put(inv8, new UserInputManager.KeyAction(inv8));
        actionMap.put(inv9, new UserInputManager.KeyAction(inv9));
    }

    private class KeyAction extends AbstractAction {
        KeyAction(String actionCommand) {
            putValue(ACTION_COMMAND_KEY, actionCommand);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvt) {
            String command = actionEvt.getActionCommand();
            try {
                Integer directionValue = Integer.parseInt(command);
                EntityManager.player.move(Vector.getVectorFromDirectionInt(directionValue));
            } catch (NumberFormatException e) {
                // It isn't a direction value, do other stuff
                // I know this is horrible programming but tell that to the java guy who decided not to make an isInteger method...
                if (command.contains("inv")) {
                    EntityManager.player.setSelectedItemIndex(Character.getNumericValue(command.charAt(3)));
                }

                switch (command) {
                    case "+":
                        target.scaleGridSize(2.0);
                        break;
                    case "-":
                        target.scaleGridSize(0.5);
                        break;
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Vector offsetFromOrigin = EntityManager.player.getGridPosition().multiplyByScalar(target.getGridSize());

        int halfGridWidth = (int) (target.getWidth() / target.getGridSize() / 2.0) + 1;
        int halfGridHeight = (int) (target.getHeight() / target.getGridSize() / 2.0) + 1;

        int x = (int)(offsetFromOrigin.getX() / target.getGridSize()) - halfGridWidth + (e.getX() / target.getGridSize());
        int y = (int) (offsetFromOrigin.getY() / target.getGridSize()) - halfGridHeight + (e.getY() / target.getGridSize());

        Vector selectedLocation = new Vector(x, y);
        Tile tile = Terrain.grid.get(selectedLocation);
        if (tile.isOccupied()) {
            List<Entity> selectedEntities = tile.getOccupyingEntities();
            if (e.getButton() == 1) {
                int i = 0;
                while (i < selectedEntities.size()) {
                    if (selectedEntities.get(i) instanceof NonPlayerEntity) {
                        //if the selected entity was not removed, increment i
                        if (!((NonPlayerEntity) selectedEntities.get(i)).click()) {
                            i++;
                        }
                    }
                }
            }
        } else {
            if (e.getButton() == 3 && EntityManager.player.getSelectedItem() instanceof PlaceableItem) {
                ((PlaceableItem) EntityManager.player.getSelectedItem()).placeAt(selectedLocation);
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
        EntityManager.player.incrementSelectedItemIndexBy(e.getWheelRotation());
    }
}
