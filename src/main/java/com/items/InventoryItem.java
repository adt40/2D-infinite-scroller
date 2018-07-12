package main.java.com.items;

public abstract class InventoryItem {

    private String name;

    public InventoryItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
