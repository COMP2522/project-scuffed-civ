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

    //shifts the map entirely
    public void shift(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public Player getOwner () {
        return owner;
    }
}
