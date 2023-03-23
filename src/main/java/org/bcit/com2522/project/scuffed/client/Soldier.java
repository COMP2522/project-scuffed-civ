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

    public void attack(Entity[][] entities, Entity entity){
        if (!withinRange(entity.getPosition())) {
            System.out.println("enemy is out of range");
            return;
        }
        if (!canAct()) {
            System.out.println("you are out of actions");
            return;
        }
        entity.takeDamage(damage);
        System.out.println("you did some damage");
        remainAction--;


        if (entity.getHealth() <= 0) {
            entities[entity.getPosition().getX()][entity.getPosition().getY()] = null;
        }
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
