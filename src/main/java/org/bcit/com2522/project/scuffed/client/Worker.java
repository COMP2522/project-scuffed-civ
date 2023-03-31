package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.awt.*;

import static org.bcit.com2522.project.scuffed.client.Window.GameImages;

public class Worker extends Unit{

    public static final int cost = 1;
    public static final int health = 100;
    public static final int speed = 5;

    public Worker(Position position, int ownerID, Color pColor, int health, int cost, int speed) {
        super(position, ownerID, pColor, health, cost, speed);
        entityType = "worker";
        texture = GameImages.get(entityType);
    }

    public JSONObject toJSONObject() {
        JSONObject workerObject = super.toJSONObject();
        return workerObject;
    }

    public static Worker fromJSONObject(JSONObject workerObject) {
        if(workerObject == null) {
            return null;
        }
        Worker worker = new Worker(Position.fromJSONObject((JSONObject) workerObject.get("position")),
                (int)(long)workerObject.get("ownerId"),
                (Color) workerObject.get("color"),
                (int) workerObject.get("maxHealth"),
                Worker.cost,
                (int) workerObject.get("speed"));
        worker.currentHealth = (int)(long) workerObject.get("currentHealth");
        return worker;
    }

        public Entity buildBuilding(Entity[][] entities) {
        Building building = null;
        if (this instanceof Worker) {
            Position free = getFreePosition(entities);
            if (canBuild(free, Building.cost)) {
                building = new Building(free, ownerID, color, Building.health, Building.cost);
                entities[free.getX()][free.getY()] = building;
                remainAction--;
                owner.spendResources(Building.cost);
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
