package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

public class Unit extends Entity{
    int speed;
    int damage;

    public Unit(Window scene, Position position, Player player) {
        super(scene, position, player);
        entityType = "unit";
        speed = 3;
        damage = 1;
    }

    public Unit(Window scene){
        super(scene);
        entityType = "unit";
        speed = 3;
        damage = 1;
    }


    public void move(){

    }
    public void attack(){

    }

    public void build(){

    }

    public JSONObject toJSONObject() {
        JSONObject unitObject = new JSONObject();
        unitObject.put("x", this.getPosition().getX());
        unitObject.put("y", this.getPosition().getY());
        return unitObject;
    }

    public static Unit fromJSONObject(JSONObject unitObject, Window scene) {
        Unit unit = new Unit(scene);
        unit.scene = scene;
        unit.setPosition(Position.fromJSONObject((JSONObject) unitObject.get("position")));
        unit.owner = Player.fromJSONObject((JSONObject) unitObject.get("owner"));
        unit.health = (Integer) unitObject.get("health");
        unit.currentHealth = (Integer) unitObject.get("currentHealth");
        unit.resourceCost = (Integer) unitObject.get("resourceCost");
        unit.speed = (Integer) unitObject.get("speed");
        unit.damage = (Integer) unitObject.get("damage");
        return unit;
    }


}
