package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.io.Serializable;

import static processing.awt.ShimAWT.loadImage;

public class Soldier extends Unit implements Serializable {
    int speed;
    int damage;

    int range;

    public Soldier(Window scene, Position position, Player player) {
        super(scene, position, player);
        unitType = "soldier";
        texture = loadImage(scene, "sprites/mrClean.png");
        damage = 50;
        range = 500;
    }

    public void move(){

    }
    public int attack(){
        remainAction--;
        return damage;
    }

    public void build(){

    }

    public JSONObject toJSONObject() {
        JSONObject soldierObject = new JSONObject();
        soldierObject.put("entityType", entityType);
        soldierObject.put("unitType", unitType);
        soldierObject.put("position", position.toJSONObject());
        soldierObject.put("owner", owner.toJSONObject());
        soldierObject.put("currentHealth", currentHealth);
        return soldierObject;
    }

    public static Soldier fromJSONObject(JSONObject soldierObject, Window scene) {
        if(soldierObject == null) {
            return null;
        }
        Soldier soldier = new Soldier(scene, Position.fromJSONObject((JSONObject) soldierObject.get("position")), Player.fromJSONObject((JSONObject) soldierObject.get("owner"), scene));
        soldier.currentHealth = (int) (long) soldierObject.get("currentHealth");
        return soldier;
    }

    public boolean withinRange(Position position) {
        if(Math.abs(position.getX() - this.position.getX()) + Math.abs(position.getY() - this.position.getY()) <= range) {
            return true;
        } else {
            System.out.println("that position is out of range");
            return false;
        }
    }
}
