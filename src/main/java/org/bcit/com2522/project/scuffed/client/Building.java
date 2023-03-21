package org.bcit.com2522.project.scuffed.client;


import org.json.simple.JSONObject;

import static processing.awt.ShimAWT.loadImage;

public class Building extends Entity{


    public Building(Window scene, Position position, Player player) {
        super(scene, position, player);
        texture = loadImage(scene, "sprites/factorio.png");
        entityType = "building";
    }

    public Building(Window scene){
        super(scene);
        entityType = "building";
        texture = loadImage(scene, "sprites/factorio.png");
    }
    public void build(){

    }

    public JSONObject toJSONObject() {
        JSONObject buildingObject = new JSONObject();
        buildingObject.put("entityType", entityType);
        buildingObject.put("position", position.toJSONObject());
        buildingObject.put("owner", owner.toJSONObject());
        buildingObject.put("health", maxHealth);
        buildingObject.put("currentHealth", currentHealth);
        buildingObject.put("resourceCost", resourceCost);
        return buildingObject;
    }

    public static Building fromJSONObject(JSONObject buildingObject, Window scene) {
        if(buildingObject == null) {
            return null;
        }
        Building building = new Building(scene, Position.fromJSONObject((JSONObject) buildingObject.get("position")), Player.fromJSONObject((JSONObject) buildingObject.get("owner"), scene));
        building.maxHealth = (int)(long) buildingObject.get("health");
        building.currentHealth = (int)(long) buildingObject.get("currentHealth");
        building.resourceCost = (int)(long) buildingObject.get("resourceCost");
        return building;
    }
}
