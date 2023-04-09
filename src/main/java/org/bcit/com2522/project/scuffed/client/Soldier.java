package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;

import java.awt.*;

import static org.bcit.com2522.project.scuffed.client.Window.GameImages;

/**
 * The type Soldier.
 */
public class Soldier extends Unit {
    /**
     * The constant cost.
     */
    public static final int cost = 1;
    /**
     * The Damage.
     */
    int damage;
    /**
     * The Range.
     */
    int range;

    /**
     * Instantiates a new Soldier.
     *
     * @param ownerId the owner id
     * @param health  the health
     * @param cost    the cost
     * @param speed   the speed
     * @param damage  the damage
     * @param range   the range
     */
    public Soldier(int ownerId, int health, int cost, int speed, int damage, int range) { // TODO this is the only
                                                                                          // constructor
        super(ownerId, health, cost, speed);
        entityType = "soldier";
        texture = GameImages.get("soldier");
        this.damage = damage;
        this.range = range;
    }

    public void attack(Entity[][] entities, Entity target) {
        if (target == null) {
            System.out.println("there is not an enemy there");
            return;
        } else if (!withinRange(target.getPosition(entities), entities)) {
            System.out.println("enemy is out of range");
            return;
        } else if (cannotAct()) {
            System.out.println("you are out of actions");
            return;
        } else if (target.getOwner() == this.getOwner()) {
            System.out.println("no friendly fire");
        } else {
            target.takeDamage(damage);
            System.out.println("you did some damage");
            remainAction--;
        }

        if (target.getHealth() <= 0) {
            entities[target.getPosition(entities).getX()][target.getPosition(entities).getY()] = null;
        }
    }

    public JSONObject toJSONObject() {
        JSONObject soldierObject = super.toJSONObject();
        soldierObject.put("damage", damage);
        soldierObject.put("range", range);
        return soldierObject;
    }

    /**
     * Creates a Soldier object from a JSONObject
     *
     * @param soldierObject the soldier object
     * @return soldier
     */
    public static Soldier fromJSONObject(JSONObject soldierObject) {
        if (soldierObject == null) {
            return null;
        }
        Soldier soldier = new Soldier(
                // Position.fromJSONObject((JSONObject) soldierObject.get("position")),
                (int) (long) soldierObject.get("ownerId"),
                // (Color) soldierObject.get("color"),
                (int) (long) soldierObject.get("maxHealth"),
                Soldier.cost,
                (int) (long) soldierObject.get("maxMove"),
                (int) (long) soldierObject.get("damage"),
                (int) (long) soldierObject.get("range"));
        soldier.currentHealth = (int) (long) soldierObject.get("currentHealth");
        return soldier;
    }

    public boolean withinRange(Position position, Entity[][] entities) {
        if (Math.abs(position.getX() - this.getPosition(entities).getX())
                + Math.abs(position.getY() - this.getPosition(entities).getY()) <= range &&
                position.getX() >= 0 && position.getX() < entities.length && position.getY() >= 0
                && position.getY() < entities[0].length) {
            return true;
        } else {
            System.out.println("that position is out of range");
            return false;
        }
    }

    /**
     * Gets damage.
     *
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Gets range.
     *
     * @return the range
     */
    public int getRange() {
        return (range);
    }

}
