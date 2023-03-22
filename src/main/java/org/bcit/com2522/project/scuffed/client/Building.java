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
        Building building = new Building(Position.fromJSONObject((JSONObject) buildingObject.get("position")), Player.fromJSONObject((JSONObject) buildingObject.get("owner")));
        building.maxHealth = (int)(long) buildingObject.get("health");
        building.currentHealth = (int)(long) buildingObject.get("currentHealth");
        building.resourceCost = (int)(long) buildingObject.get("resourceCost");
        return building;
    }
}
