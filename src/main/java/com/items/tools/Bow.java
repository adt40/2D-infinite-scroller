package main.java.com.items.tools;

public class Bow extends Tool {

    private int range;

    public Bow(int range) {
        super(1.0, 0.1, "Bow");
        this.range = range;
    }

    public int getRange() {
        return range;
    }
}
