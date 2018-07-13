package main.java.com.items;

public abstract class InventoryItem {

    private String name;
    private int amount;

    public InventoryItem(String name) {
        this.name = name;
        amount = 1;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }
}
