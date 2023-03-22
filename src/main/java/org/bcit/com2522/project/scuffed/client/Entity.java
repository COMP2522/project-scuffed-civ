package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;
import processing.core.PImage;

public abstract class Entity { //TODO: make this class abstract
    protected int maxAction;
    protected int remainAction;
    protected int ownerID;
    protected int maxHealth;
    protected int currentHealth;
    protected int resourceCost;

    protected Position position;
    protected PImage texture;
    protected String entityType; //building, soldier, worker

    public Entity(Position position, Player owner) {
        this.position = position;
        this.ownerID = owner.getID();
        maxAction = 1;
        remainAction = 1;
        maxHealth = 100;
        currentHealth = maxHealth;
    }

    public void resize(int zoomAmount) {
        texture.resize(zoomAmount, 0);
    }

    public void draw(int zoomAmount, int playerNum, Window scene) {
        if(playerNum == 0) {
            scene.tint(255, 0, 0);
            scene.image(texture, this.getPosition().getX() * zoomAmount, this.getPosition().getY() * zoomAmount);
        } else if(playerNum == 1) {
            scene.tint(0, 0, 255);
            scene.image(texture, this.getPosition().getX() * zoomAmount, this.getPosition().getY() * zoomAmount);
        } else if(playerNum == 2) {
            scene.tint(0, 255, 0);
            scene.image(texture, this.getPosition().getX() * zoomAmount, this.getPosition().getY() * zoomAmount);
        } else if(playerNum == 3) {
            scene.tint(255, 255, 0);
            scene.image(texture, this.getPosition().getX() * zoomAmount, this.getPosition().getY() * zoomAmount);
        } else if(playerNum == 4) {
            scene.tint(191, 64, 191);
            scene.image(texture, this.getPosition().getX() * zoomAmount, this.getPosition().getY() * zoomAmount);
        } else if(playerNum == 5) {
            scene.tint(255, 192, 203);
            scene.image(texture, this.getPosition().getX() * zoomAmount, this.getPosition().getY() * zoomAmount);
        }
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

    /**
     * C
     * @return
     */
    public JSONObject toJSONObject() {
        JSONObject entityObject;
        if(this instanceof Unit) {
            entityObject = ((Unit)this).toJSONObject();
        } else if(this instanceof Building) {
            entityObject = ((Building)this).toJSONObject();
        } else {
            throw new IllegalArgumentException("this is not a valid entity");
        }

        entityObject.put("position", position.toJSONObject());
        entityObject.put("ownerId", ownerID);
        entityObject.put("currentHealth", currentHealth);
        entityObject.put("maxHealth", maxHealth);
        entityObject.put("maxAction", maxAction);
        entityObject.put("remainAction", remainAction);
        entityObject.put("entityType", entityType);
        entityObject.put("resourceCost", resourceCost);
        entityObject.put("texture", texture.toString());
        return entityObject;
    }


    public static Entity fromJSONObject(JSONObject entityObject) {
        if(entityObject == null || entityObject.isEmpty()) {
            return null;
        }
        String entityType = (String) entityObject.get("entityType");
        switch(entityType) {
            case "building":
                return Building.fromJSONObject(entityObject);
            case "soldier":
                return Soldier.fromJSONObject(entityObject);
            case "worker":
                return Worker.fromJSONObject(entityObject);
            default:
                throw new IllegalArgumentException("Invalid entity type must be unit or building");
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
