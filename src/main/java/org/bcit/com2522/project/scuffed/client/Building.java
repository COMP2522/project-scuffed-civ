package org.bcit.com2522.project.scuffed.client;


import org.json.simple.JSONObject;

import java.awt.*;

import static org.bcit.com2522.project.scuffed.client.Window.GameImages;

public class Building extends Entity{
    public static final int health = 200;
    public static final int cost = 2;

    public Building(int ownerID, int health, int cost) {
        super(ownerID, health, cost);
        texture = GameImages.get("building");
        entityType = "building";
    }


    /**
     * Converts the Building object to a JSONObject
     * @return buildingObject - the JSONObject representation of the Building object
     */
    public JSONObject toJSONObject() {
        JSONObject buildingObject = super.toJSONObject();
        return buildingObject;
    }

    public static Building fromJSONObject(JSONObject buildingObject) {
        if(buildingObject == null) {
            return null;
        }
        Building building = new Building(
                //Position.fromJSONObject((JSONObject) buildingObject.get("position")),
                (int)(long) buildingObject.get("ownerId"),
                //(Color) buildingObject.get("color"),
                (int) buildingObject.get("maxHealth"),
                Building.cost);
        building.currentHealth = (int) (long) buildingObject.get("currentHealth");
        return building;
    }

    public void buildWorker(Entity[][] entities) {
        Position free = getFreePosition(entities);
        if (canBuild(free, Worker.cost)) {
            entities[free.getX()][free.getY()] = new Worker(ownerID, Worker.health, Worker.cost, Worker.speed);
            remainAction--;
            owner.spendResources(1);
        }
    }

    public void buildSoldier(Entity[][] entities, int health, int damage, int speed, int range) {
        Position free = getFreePosition(entities);
        if (canBuild(free, Soldier.cost)) {
            entities[free.getX()][free.getY()] = new Soldier(ownerID, health, Soldier.cost, speed, damage, range);
            remainAction--;
            owner.spendResources(Soldier.cost);
        }
    }

}
