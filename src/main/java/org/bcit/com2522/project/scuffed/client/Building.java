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
        buildingObject.put("x", this.getPosition().getX());
        buildingObject.put("y", this.getPosition().getY());
        return buildingObject;
    }

    public static Building fromJSONObject(JSONObject buildingObject, Window scene) {
        Building building = new Building(scene);
        building.setPosition(Position.fromJSONObject((JSONObject) buildingObject.get("position")));
        building.owner = Player.fromJSONObject((JSONObject) buildingObject.get("owner"));
        building.health = (Integer) buildingObject.get("health");
        building.currentHealth = (Integer) buildingObject.get("currentHealth");
        building.resourceCost = (Integer) buildingObject.get("resourceCost");
        return building;
    }
}
