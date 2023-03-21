package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;
import processing.core.PImage;

import static processing.awt.ShimAWT.loadImage;

public class Entity { //TODO: make this class abstract

    int maxAction;
    int remainAction;
    Position position;

    String entityType; //building or unit or entity(for now)

    Player owner;

    int ownerNum;

    Window scene;

    PImage texture;

    int health;
    int currentHealth;
    int resourceCost;

    public Entity(Window scene, Position position, Player player) {
        this.position = position;
        this.scene = scene;
        //texture = loadImage(scene, "sprites/mario.png");
        this.owner = player;
        this.ownerNum = player.getPlayerNum();
        this.entityType = "entity"; //TODO: remove this once entity is abstracted as every entityType should be building or unit
        maxAction = 1;
        remainAction = 1;
    }

    public Entity(Window scene) {
        texture = loadImage(scene, "sprites/mario.png");
        this.entityType = "entity"; //TODO: remove this once entity is abstracted as every entityType should be building or unit
    }

    public void resize(int zoomAmount) {
        texture.resize(zoomAmount, 0);
    }

    public void draw(int zoomAmount, int playerNum) {
        if(playerNum == 0) {
            scene.tint(255, 0, 0);
            this.scene.image(texture, this.getPosition().getX() * zoomAmount, this.getPosition().getY() * zoomAmount);
        } else if(playerNum == 1) {
            scene.tint(0, 0, 255);
            this.scene.image(texture, this.getPosition().getX() * zoomAmount, this.getPosition().getY() * zoomAmount);
        } else if(playerNum == 2) {
            scene.tint(0, 255, 0);
            this.scene.image(texture, this.getPosition().getX() * zoomAmount, this.getPosition().getY() * zoomAmount);
        } else if(playerNum == 3) {
            scene.tint(255, 255, 0);
            this.scene.image(texture, this.getPosition().getX() * zoomAmount, this.getPosition().getY() * zoomAmount);
        } else if(playerNum == 4) {
            scene.tint(191, 64, 191);
            this.scene.image(texture, this.getPosition().getX() * zoomAmount, this.getPosition().getY() * zoomAmount);
        } else if(playerNum == 5) {
            scene.tint(255, 192, 203);
            this.scene.image(texture, this.getPosition().getX() * zoomAmount, this.getPosition().getY() * zoomAmount);
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

    public Player getOwner () {
        return owner;
    }

    public JSONObject toJSONObject() {
        switch(entityType) {
            case "unit":
                Unit unit = (Unit) this;
                return unit.toJSONObject();
            case "building":
                Building building = (Building) this;
                return building.toJSONObject();
            default:
                throw new IllegalArgumentException("Invalid entity type must be unit or building");
        }

    }
    public static Entity fromJSONObject(JSONObject entityObject, Window scene) { //TODO: add reference to window/scene
        if(entityObject == null || entityObject.isEmpty()) {
            return null;
        }
        String entityType = (String) entityObject.get("entityType");
        switch(entityType) {
            case "unit":
                return Unit.fromJSONObject(entityObject, scene);
            case "building":
                return Building.fromJSONObject(entityObject, scene);
            default:
                throw new IllegalArgumentException("Invalid entity type must be unit or building");
        }
    }

    public int getActionsRemain() {
        return remainAction;
    }

    public void act() {
        remainAction--;
    }
}
