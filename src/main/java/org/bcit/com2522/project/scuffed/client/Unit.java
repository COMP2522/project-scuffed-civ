package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;
import static processing.awt.ShimAWT.loadImage;


public class Unit extends Entity { //things that can move
    int maxMove;
    int remainMove;

    public Unit(Window scene, Position position, Player player) {
        super(scene, position, player);
        entityType = "unit";
        maxMove = 6;
        remainMove = maxMove;
    }

    public Unit(Window scene){
        super(scene);
        entityType = "unit";
        maxMove = 6;
        remainMove = maxMove;
    }

    public void resetMove() {
        remainMove = maxMove;
    }

    public Boolean moveTo(Position position) {
        //if the position is in range
        if(Math.abs(position.getX() - this.position.getX()) + Math.abs(position.getY() - this.position.getY()) <= remainMove) {
            remainMove -= Math.abs(position.getX() - this.position.getX()) + Math.abs(position.getY() - this.position.getY());
            this.position = position;
            return true;
        } else {
            System.out.println("that position is out of range");
            return false;
        }
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
//        unit.speed = (Integer) unitObject.get("speed");
//        unit.damage = (Integer) unitObject.get("damage");
        return unit;
    }


}
