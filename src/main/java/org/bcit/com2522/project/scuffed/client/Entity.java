package org.bcit.com2522.project.scuffed.client;

import processing.core.PImage;

import static processing.awt.ShimAWT.loadImage;

public class Entity {
    Position position;

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

    public Position getPosition() {
        return position;
    }

    public Player getOwner () {
        return owner;
    }
}
