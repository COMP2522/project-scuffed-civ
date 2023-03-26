package org.bcit.com2522.project.scuffed.client;


import org.json.simple.JSONObject;

import static org.bcit.com2522.project.scuffed.client.Window.PImages;
import static processing.awt.ShimAWT.loadImage;

public class Building extends Entity{

    public Building(Position position, Player owner) {
        super(position, owner);
        texture = PImages.get("building");
        entityType = "building";
    }

    public Building(Position position, int ownerID) {
        super(position, ownerID);
        texture = PImages.get("building");
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
        Building building = new Building(Position.fromJSONObject((JSONObject) buildingObject.get("position")), (int)(long) buildingObject.get("ownerId"));
        building.currentHealth = (int)(long) buildingObject.get("currentHealth");
        return building;
    }

    public void buildWorker(Entity[][] entities) {
        Position free = getFreePosition(entities);
        if (canBuild(free, 1)) {
            entities[free.getX()][free.getY()] = new Worker(free, owner);
            remainAction--;
            owner.spendResources(1);
        }
    }

    public void buildSoldier(Entity[][] entities) {
        Position free = getFreePosition(entities);
        if (canBuild(free, 1)) {
            entities[free.getX()][free.getY()] = new Soldier(free, owner);
            remainAction--;
            owner.spendResources(1);
        }
    }
}
