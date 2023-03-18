package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;
import processing.core.PImage;

import static processing.awt.ShimAWT.loadImage;

public class Entity {
    private Position position;

    Player owner;

    Window scene;

    PImage texture;

    int health;
    int currentHealth;
    int resourceCost;

    public Entity(Window scene, Position position, Player player) {
        this.position = position;
        this.scene = scene;
        texture = loadImage(scene, "sprites/mario.png");

        this.owner = player;
    }

    public void draw(){
        this.scene.image(texture, this.getPosition().getX()*32,this.getPosition().getY()*32); //this is the source of the entity not scaling with zoom
    }

    public void moveTo(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public Player getOwner () {
        return owner;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("position", position.toJSONObject());
        obj.put("owner", owner.toJSONObject());
        obj.put("health", health);
        obj.put("currentHealth", currentHealth);
        obj.put("resourceCost", resourceCost);
        return obj;
    }
}
