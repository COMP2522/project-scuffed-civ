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


    public Entity buildBuilding(Entity[][] entities) {
        Building building = null;
        if (this instanceof Worker) {
            Position free = getFreePosition(entities);
            if (canBuild(free, 2)) {
                building = new Building(free, owner);
                entities[free.getX()][free.getY()] = building;
                remainAction--;
                owner.spendResources(2);
            }
        }
        return building;
    }

    public void collect(Tile tile) {
        if (!canAct()) {
            System.out.println("you do not have enough actions left");
            return;
        }
        if (tile.getType() <= 0) {
            System.out.println("there is nothing to collect here");
            return;
        }
        owner.increaseResources(tile.takeResources());
        remainAction--;
        remainMove = 0;
    }
}
