package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.io.Serializable;

import static org.bcit.com2522.project.scuffed.client.Window.PImages;
import static processing.awt.ShimAWT.loadImage;

public class Soldier extends Unit{
    int damage;

    int range;

    public Soldier(Position position, Player owner) {
        super(position, owner);
        entityType = "soldier";
        texture = PImages.get("soldier");
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
        soldierObject.put("entityType", "soldier");
        soldierObject.put("damage", damage);
        soldierObject.put("range", range);
        return soldierObject;
    }

    public static Soldier fromJSONObject(JSONObject soldierObject) {
        if(soldierObject == null) {
            return null;
        }
        Soldier soldier = new Soldier(Position.fromJSONObject((JSONObject) soldierObject.get("position")), Player.fromJSONObject((JSONObject) soldierObject.get("owner")));
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
