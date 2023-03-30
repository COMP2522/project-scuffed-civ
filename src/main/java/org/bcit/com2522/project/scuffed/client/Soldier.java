package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.awt.*;

import static org.bcit.com2522.project.scuffed.client.Window.GameImages;

public class Soldier extends Unit{
    public static final int cost = 1;
    int damage;

    int range;

    public Soldier(Position position, int ownerId, Color pColor, int health, int cost, int speed, int damage, int range) { //TODO this is the only constructor
        super(position, ownerId, pColor, health, cost, speed);
        entityType = "soldier";
        texture = GameImages.get("soldier");
        this.damage = damage;
        this.range = range;
    }

    public void attack(Entity[][] entities, Entity target){
        if (!withinRange(target.getPosition())) {
            System.out.println("enemy is out of range");
            return;
        } else if (!canAct()) {
            System.out.println("you are out of actions");
            return;
        } else {
            target.takeDamage(damage);
            System.out.println("you did some damage");
            remainAction--;
        }


        if (target.getHealth() <= 0) {
            entities[target.getPosition().getX()][target.getPosition().getY()] = null;
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
        Soldier soldier = new Soldier(
                Position.fromJSONObject((JSONObject) soldierObject.get("position")),
                (int)(long) soldierObject.get("ownerId"),
                (Color) soldierObject.get("color"),
                (int) soldierObject.get("maxHealth"),
                Soldier.cost,
                (int) soldierObject.get("speed"),
                (int) soldierObject.get("damage"),
                (int) soldierObject.get("range"));
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

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return (range);
    }


}
