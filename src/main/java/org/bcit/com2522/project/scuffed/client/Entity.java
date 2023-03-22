package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;
import processing.core.PImage;

import java.awt.*;

public abstract class Entity { //TODO: make this class abstract
    protected int maxAction;
    protected int remainAction;
    protected int ownerID;

    protected Color color;
    protected int maxHealth;
    protected int currentHealth;
    protected int resourceCost;

    protected Position position;
    protected PImage texture;
    protected String entityType; //building, soldier, worker

    public Entity(Position position, Player owner) {
        this.position = position;
        this.ownerID = owner.getID();
        this.color = owner.getColor();
        maxAction = 1;
        remainAction = 1;
        maxHealth = 100;
        currentHealth = maxHealth;
    }
    public Entity(Position position, int ownerID) {
        this.position = position;
        this.ownerID = ownerID;
        maxAction = 1;
        remainAction = 1;
        maxHealth = 100;
        currentHealth = maxHealth;
    }

    public void resize(int zoomAmount) {
        texture.resize(zoomAmount, 0);
    }

    public void draw(int zoomAmount, Color color, Window scene) {
        scene.tint(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        scene.image(texture, this.position.getX() * zoomAmount, this.position.getY() * zoomAmount);
        scene.noTint();
    }

    public void resetAction() {
        remainAction = maxAction;
    }

    //shifts the map entirely
    public void shift(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getOwnerID () {
        return ownerID;
    }


    public JSONObject toJSONObject() {
        JSONObject entityObject = new JSONObject();
        entityObject.put("position", position.toJSONObject());
        entityObject.put("ownerId", ownerID);
        entityObject.put("color", "#" + Integer.toHexString(color.getRGB()).substring(2));
        entityObject.put("currentHealth", currentHealth);
        entityObject.put("remainAction", remainAction);
        entityObject.put("entityType", entityType);
        return entityObject;
    }


    public static Entity fromJSONObject(JSONObject entityObject) {
        if(entityObject == null || entityObject.isEmpty()) {
            return null;
        }
        String entityType = (String) entityObject.get("entityType");
        System.out.println(entityType);
        switch(entityType) {
            case "building":
                Building building = Building.fromJSONObject(entityObject);
                building.color = Color.decode((String) entityObject.get("color"));
                return building;
            case "soldier":
                Soldier soldier = Soldier.fromJSONObject(entityObject);
                soldier.color = Color.decode((String) entityObject.get("color"));
                return soldier;
            case "worker":
                Worker worker = Worker.fromJSONObject(entityObject);
                worker.color = Color.decode((String) entityObject.get("color"));
                return worker;
            default:
                throw new IllegalArgumentException("this is not a valid entityType: " + entityType);
        }

    }

    public boolean canAct() {
        return remainAction >= 1;
    }

    public void act() {
        remainAction--;
    }

    public boolean dealDamage(int damageDealt) { //returns true if dead
        currentHealth -= damageDealt;

        if (currentHealth <= 0) {
            return true;
        }
        return false;
    }


}
