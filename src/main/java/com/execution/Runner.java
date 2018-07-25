package main.java.com.execution;

import main.java.com.graphics.DisplayGraphics;

import javax.swing.*;

public class Runner {
    public static void main(String args[]) {
        int windowSize = 700;
        int gridSize = 32;
        int numOctaves = 5;
        Double frequency = 0.03;

        DisplayGraphics panel = new DisplayGraphics(gridSize, numOctaves, frequency);
        JFrame frame = new JFrame("Infinite 2D World");
        frame.getContentPane().add(panel);
        frame.setSize(windowSize, windowSize);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
