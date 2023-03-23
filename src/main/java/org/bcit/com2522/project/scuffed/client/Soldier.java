package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import static org.bcit.com2522.project.scuffed.client.Window.PImages;

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

    public Soldier(Position position, int ownerId) {
        super(position, ownerId);
        entityType = "soldier";
        texture = PImages.get("soldier");
        damage = 50;
        range = 500;
    }

    public void attack(Entity entity){
        remainAction--;

        if (withinRange(entity.getPosition()) && canAct()) {
            entity.takeDamage(damage);
            System.out.println("you did some damage");
        } else {
            System.out.println("enemy is either out of range or you are out of actions");
        }
    }

    public void build(){

    }

    public JSONObject toJSONObject() {
        JSONObject soldierObject = super.toJSONObject();
        soldierObject.put("damage", damage);
        soldierObject.put("range", range);
        return soldierObject;
    }

    public static Soldier fromJSONObject(JSONObject soldierObject) {
        if(soldierObject == null) {
            return null;
        }
        Soldier soldier = new Soldier(Position.fromJSONObject((JSONObject) soldierObject.get("position")), (int)(long) soldierObject.get("ownerId"));
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
