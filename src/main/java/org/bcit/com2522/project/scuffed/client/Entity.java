package org.bcit.com2522.project.scuffed.client;

import org.json.simple.JSONObject;
import processing.core.PImage;

import static processing.awt.ShimAWT.loadImage;

public class Entity { //TODO: make this class abstract


    private Position position;

    String entityType; //building or unit or entity(for now)

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
        this.entityType = "entity"; //TODO: remove this once entity is abstracted as every entityType should be building or unit
    }

    public Entity(Window scene) {
        texture = loadImage(scene, "sprites/mario.png");
        this.entityType = "entity"; //TODO: remove this once entity is abstracted as every entityType should be building or unit
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

    public void setPosition(Position position) {
        this.position = position;
    }

    public Player getOwner () {
        return owner;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("position", position.toJSONObject());
        obj.put("type", entityType);
        obj.put("owner", owner.toJSONObject());
        obj.put("health", health);
        obj.put("currentHealth", currentHealth);
        obj.put("resourceCost", resourceCost);
        return obj;
    }
    public static Entity fromJSONObject(JSONObject entityObject, Window scene) { //TODO: add reference to window/scene
        String entityType = (String) entityObject.get("type");
        if(entityType.equalsIgnoreCase("unit")) {
            return Unit.fromJSONObject(entityObject, scene);
        } else if(entityType.equalsIgnoreCase("building")) {
            return Building.fromJSONObject(entityObject, scene);
        } else if(entityType.equalsIgnoreCase("entity")) { //TODO remove this once entity is abstracted as every entity should be building or unit
            Entity entity = new Entity(scene);
            entity.position = Position.fromJSONObject((JSONObject) entityObject.get("position"));
            entity.owner = Player.fromJSONObject((JSONObject) entityObject.get("owner"));
            entity.health = (int)(long) entityObject.get("health");
            entity.currentHealth = (int)(long) entityObject.get("currentHealth");
            entity.resourceCost = (int)(long) entityObject.get("resourceCost");
            return entity;
        } else {
            throw new IllegalArgumentException("Invalid entity type");
        }
    }
}
