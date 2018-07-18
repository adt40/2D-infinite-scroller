package main.java.com.ai;

import main.java.com.entities.alive.FishEntity;

import java.util.TimerTask;

public class FishAI extends TimerTask {

    private FishEntity fishEntity;

    public FishAI(FishEntity fishEntity) { this.fishEntity = fishEntity; }

    @Override
    public void run() {
        StandardAI.wander(fishEntity);
    }
}
