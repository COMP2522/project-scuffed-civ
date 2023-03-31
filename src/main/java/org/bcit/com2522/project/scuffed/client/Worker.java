package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.awt.*;

import static org.bcit.com2522.project.scuffed.client.Window.GameImages;

public class Worker extends Unit{

    public static final int cost = 1;
    public static final int health = 100;
    public static final int speed = 5;

    public Worker(int ownerID, int health, int cost, int speed) {
        super(ownerID, health, cost, speed);
        entityType = "worker";
        texture = GameImages.get(entityType);
    }

    public JSONObject toJSONObject() {
        JSONObject workerObject = super.toJSONObject();
        workerObject.put("speed", speed);
        return workerObject;
    }

    public static Worker fromJSONObject(JSONObject workerObject) {
        if(workerObject == null) {
            return null;
        }
        Worker worker = new Worker(
                (int)(long)workerObject.get("ownerId"),
                //(Color) workerObject.get("color"),
                (int)(long) workerObject.get("maxHealth"),
                Worker.cost,
                (int)(long) workerObject.get("speed"));
        worker.currentHealth = (int)(long) workerObject.get("currentHealth");
        return worker;
    }

        public Entity buildBuilding(Entity[][] entities) {
        Building building = null;
        if (this instanceof Worker) {
            Position free = getFreePosition(entities);
            if (canBuild(free, Building.cost)) {
                building = new Building(ownerID, Building.health, Building.cost);
                entities[free.getX()][free.getY()] = building;
                remainAction--;
                owner.spendResources(Building.cost);
            }
        }
        return building;
    }

    public void collect(Tile tile) {
        if (cannotAct()) {
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
