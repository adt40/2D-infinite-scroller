package main.java.com.ai;

import main.java.com.entities.alive.WalkerEntity;

import java.util.TimerTask;

public class WalkerAI extends TimerTask {

    private final WalkerEntity walkerEntity;

    public WalkerAI(WalkerEntity walkerEntity) {
        this.walkerEntity = walkerEntity;
    }

    public void run() {
        StandardAI.wander(walkerEntity);
    }
}
