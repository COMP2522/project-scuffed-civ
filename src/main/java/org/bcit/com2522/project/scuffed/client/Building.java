package org.bcit.com2522.project.scuffed.client;


import static processing.awt.ShimAWT.loadImage;

public class Building extends Entity{


    public Building(Window scene, Position position, Player player) {
        super(scene, position, player);
        texture = loadImage(scene, "sprites/factorio.png");
    }

    public void build(){

    }
}
