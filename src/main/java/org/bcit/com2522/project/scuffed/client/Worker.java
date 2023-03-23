package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.io.Serializable;

import static org.bcit.com2522.project.scuffed.client.Window.PImages;
import static processing.awt.ShimAWT.loadImage;

public class Worker extends Unit{
    public Worker(Position position, Player owner) {
        super(position, owner);
        entityType = "worker";
        texture = PImages.get(entityType);
    }
    public Worker(Position position, int ownerID) {
        super(position, ownerID);
        entityType = "worker";
        texture = PImages.get(entityType);
    }

    public JSONObject toJSONObject() {
        JSONObject workerObject = super.toJSONObject();
        return workerObject;
    }

    public static Worker fromJSONObject(JSONObject workerObject) {
        if(workerObject == null) {
            return null;
        }
        Worker worker = new Worker(Position.fromJSONObject((JSONObject) workerObject.get("position")), (int)(long)workerObject.get("ownerId"));
        worker.currentHealth = (int)(long) workerObject.get("currentHealth");
        return worker;
    }

    public void collect(Tile tile) {
        if (canAct()) {
            owner.increaseResources(tile.takeResources());
            remainAction--;
            remainMove = 0;
        } else
            System.out.println("you do not have enough actions left");
    }
}
