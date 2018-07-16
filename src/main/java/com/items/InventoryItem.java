package main.java.com.items;

import main.java.com.entities.EntityManager;

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
        if (this.amount > 0) {
            this.amount += amount;
        }
        if (this.amount <= 0) {
            EntityManager.player.removeItem(this);
        }
    }
}
