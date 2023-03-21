package org.bcit.com2522.project.scuffed.client;


import org.json.simple.JSONObject;

import static org.bcit.com2522.project.scuffed.client.Window.PImages;
import static processing.awt.ShimAWT.loadImage;

public class Building extends Entity{

    public Building(Position position, Player player) {
        super(position, player);
        texture = PImages.get("building");
        entityType = "building";
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

    public static Building fromJSONObject(JSONObject buildingObject) {
        if(buildingObject == null) {
            return null;
        }
        Building building = new Building(Position.fromJSONObject((JSONObject) buildingObject.get("position")), Player.fromJSONObject((JSONObject) buildingObject.get("owner")));
        building.maxHealth = (int)(long) buildingObject.get("health");
        building.currentHealth = (int)(long) buildingObject.get("currentHealth");
        building.resourceCost = (int)(long) buildingObject.get("resourceCost");
        return building;
    }
}
